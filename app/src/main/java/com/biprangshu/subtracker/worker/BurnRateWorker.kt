package com.biprangshu.subtracker.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.biprangshu.subtracker.data.local.dao.ForecastDao
import com.biprangshu.subtracker.data.local.entity.ForecastEntity
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.generationConfig
import com.google.firebase.ai.type.thinkingConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltWorker
class BurnRateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val subscriptionRepository: SubscriptionRepository,
    private val forecastDao: ForecastDao
) : CoroutineWorker(context, params) {

    @Serializable
    data class ForecastItem(
        val month: String,
        val year: Int,
        val total_spend: Double
    )

    override suspend fun doWork(): Result {
        return try {
            val subscriptions = subscriptionRepository.getAllSubscriptions().first()
            if (subscriptions.isEmpty()) return Result.success()


            val monthlyTotal = subscriptions.filter { it.billingCycle == "Monthly" }.sumOf { it.price }
            val yearlyAmortized = subscriptions.filter { it.billingCycle == "Yearly" }.sumOf { it.price } / 12.0
            val averageSpend = monthlyTotal + yearlyAmortized


            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val subData = subscriptions.joinToString("\n") {
                val firstPay = dateFormat.format(Date(it.firstPaymentDate))
                "- ${it.name}: ${it.price} ${it.currency}, Cycle: ${it.billingCycle}, First Payment: $firstPay"
            }

            val generationConfig = generationConfig {
                thinkingConfig = thinkingConfig {
                    thinkingBudget = 512
                }
            }

            val generativeModel = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel(
                    "gemini-2.5-flash",
                    generationConfig = generationConfig
                )

            val prompt = """
                Today is $currentDate.
                Calculate the exact cash flow required for EACH of the next 12 months (starting next month).
                
                Subscriptions:
                $subData
                
                Rules:
                1. 'Monthly' items occur every month.
                2. 'Yearly' items occur only in the specific month derived from 'First Payment'.
                3. Sum the costs for each month.
                
                Return ONLY a JSON list:
                [
                  { "month": "Feb", "year": 2026, "total_spend": 150.0 },
                  ...
                ]
            """.trimIndent()

            val response = generativeModel.generateContent(prompt)
            val cleanJson = (response.text ?: "[]").replace("```json", "").replace("```", "").trim()

            val forecastItems = Json { ignoreUnknownKeys = true }.decodeFromString<List<ForecastItem>>(cleanJson)

            val entities = forecastItems.map {
                ForecastEntity(
                    month = it.month,
                    year = it.year,
                    predictedSpend = it.total_spend,
                    averageSpend = averageSpend
                )
            }

            forecastDao.clearForecasts()
            if (entities.isNotEmpty()) {
                forecastDao.insertForecasts(entities)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}