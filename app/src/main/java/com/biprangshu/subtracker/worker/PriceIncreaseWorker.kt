package com.biprangshu.subtracker.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.biprangshu.subtracker.data.local.PriceAlertDao
import com.biprangshu.subtracker.data.local.PriceAlertEntity
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.domain.repository.UserDataRepository
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@HiltWorker
class PriceIncreaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val subscriptionRepository: SubscriptionRepository,
    private val userDataRepository: UserDataRepository,
    private val priceAlertDao: PriceAlertDao
) : CoroutineWorker(context, params) {

    @Serializable
    data class PriceCheckResult(
        val subscription_id: Int,
        val service_name: String,
        val current_tracked_price: Double,
        val actual_market_price: Double,
        val alert_message: String,
        val is_urgent: Boolean
    )

    override suspend fun doWork(): Result {
        return try {
            val subscriptions = subscriptionRepository.getAllSubscriptions().first()
            if (subscriptions.isEmpty()) return Result.success()


            val subData = subscriptions.joinToString("\n") {
                "- [ID: ${it.id}] ${it.name}: ${it.price} ${it.currency} (${it.billingCycle})"
            }

            val user = userDataRepository.getUser().first()
            val userCurrency = user?.preferredCurrency ?: "$"

            val generativeModel = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel("gemini-2.5-flash")

            // 3. Prompt Engineering
            val prompt = """
                You are an Inflation Watchdog. Analyze this list of user subscriptions and prices:
                $subData
                
                Task:
                Compare the user's tracked price against your knowledge of recent (2024-2025) price hikes or standard market rates.
                
                Identify ONLY services where:
                1. The user's price is LOWER than the current actual price (meaning they updated it long ago).
                2. There is a publicly announced price hike coming soon.
                
                Ignore small currency conversion differences. Focus on confirmed price changes.
                
                Return a JSON list:
                [
                  {
                    "subscription_id": 12 (Use the ID provided in brackets),
                    "service_name": "Spotify",
                    "current_tracked_price": 10.99,
                    "actual_market_price": 11.99,
                    "alert_message": "Spotify Premium is now $11.99. Your tracked price is outdated.",
                    "is_urgent": true
                  }
                ]
                
                If no discrepancies found, return [].
            """.trimIndent()


            val response = generativeModel.generateContent(prompt)
            val cleanJson = (response.text ?: "[]").replace("```json", "").replace("```", "").trim()
            val results = Json { ignoreUnknownKeys = true }.decodeFromString<List<PriceCheckResult>>(cleanJson)


            val alerts = results.filter { it.is_urgent }.map {
                PriceAlertEntity(
                    subscriptionId = it.subscription_id,
                    subscriptionName = it.service_name,
                    oldPrice = it.current_tracked_price,
                    newPrice = it.actual_market_price,
                    currency = userCurrency,
                    message = it.alert_message
                )
            }

            priceAlertDao.clearAlerts()
            if (alerts.isNotEmpty()) {
                priceAlertDao.insertAlerts(alerts)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}