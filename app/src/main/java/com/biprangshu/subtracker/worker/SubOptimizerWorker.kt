package com.biprangshu.subtracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.biprangshu.subtracker.data.local.InsightDao
import com.biprangshu.subtracker.data.local.InsightEntity
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.domain.repository.UserDataRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
// Import for your specific AI syntax
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend


@HiltWorker
class SubOptimizerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val subscriptionRepository: SubscriptionRepository,
    private val userDataRepository: UserDataRepository,
    private val insightDao: InsightDao
) : CoroutineWorker(context, params) {

    //json parse model
    @Serializable
    data class AIResponseItem(
        val type: String,
        val title: String,
        val description: String,
        val savings: Double
    )

    override suspend fun doWork(): Result {
        return try {

            val subscriptions = subscriptionRepository.getAllSubscriptions().first()
            if (subscriptions.isEmpty()) {
                return Result.success()
            }

            val user = userDataRepository.getUser().first()
            val userCurrency = user?.preferredCurrency ?: "$"


            val subListString = subscriptions.joinToString("\n") {
                "- Name: ${it.name}, Price: ${it.price} ${it.currency}, Category: ${it.category}"
            }


            val generativeModel = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel("gemini-3-flash-preview")


            val prompt = """
                You are a Subscription Optimization Agent. 
                
                **User Context**:
                - User Currency: "$userCurrency"
                - Task: Infer the user's region based on the currency (e.g., '₹' implies India, '£' implies UK, '€' implies Europe). If the currency is generic (like '$'), check the subscription prices and names for regional cues, otherwise default to US.
                
                **Subscriptions**:
                $subListString
                
                **Analysis Goals**:
                1. Redundancies (e.g., multiple music apps).
                2. Bundle opportunities SPECIFIC TO THE INFERRED REGION. (e.g., In India, suggest Disney+ Hotstar or Jio bundles. Do NOT suggest US-only bundles like 'Hulu' or 'HBO Max' if the user is detected to be in a region where these are not available).
                3. High cost warnings (relative to local purchasing power).
                
                Return ONLY a valid JSON list. Do not use Markdown formatting (no ```json).
                Structure:
                [
                  {
                    "type": "REDUNDANCY" | "BUNDLE" | "SUGGESTION",
                    "title": "Short Title",
                    "description": "Short explanation (tailored to the region)",
                    "savings": 0.0 (Numeric monthly savings estimate)
                  }
                ]
            """.trimIndent()

            // 5. Generate Content
            val response = generativeModel.generateContent(prompt)
            Log.d("AI", "AI Response: $response")
            val responseText = response.text ?: "[]"

            val cleanJson = responseText.replace("```json", "").replace("```", "").trim()

            val aiInsights = Json { ignoreUnknownKeys = true }.decodeFromString<List<AIResponseItem>>(cleanJson)

            val entities = aiInsights.map {
                InsightEntity(
                    type = it.type,
                    title = it.title,
                    description = it.description,
                    potentialSavings = it.savings
                )
            }

            insightDao.clearInsights()
            if (entities.isNotEmpty()) {
                insightDao.insertInsights(entities)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}