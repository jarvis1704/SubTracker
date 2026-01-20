package com.biprangshu.subtracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.biprangshu.subtracker.data.local.InsightDao
import com.biprangshu.subtracker.data.local.InsightEntity
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
// Import for your specific AI syntax
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

// Assuming appropriate imports for your extension functions based on your snippet
// e.g., import com.google.firebase.ai.ai
// e.g., import com.google.firebase.ai.GenerativeBackend

@HiltWorker
class SubOptimizerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val subscriptionRepository: SubscriptionRepository,
    private val insightDao: InsightDao
) : CoroutineWorker(context, params) {

    // Internal model for JSON parsing
    @Serializable
    data class AIResponseItem(
        val type: String,
        val title: String,
        val description: String,
        val savings: Double
    )

    override suspend fun doWork(): Result {
        return try {
            // 1. Fetch current subscriptions
            val subscriptions = subscriptionRepository.getAllSubscriptions().first()
            if (subscriptions.isEmpty()) {
                // No subscriptions to analyze
                return Result.success()
            }

            // 2. Prepare Data String for the Prompt
            val subListString = subscriptions.joinToString("\n") {
                "- Name: ${it.name}, Price: ${it.price} ${it.currency}, Category: ${it.category}"
            }

            // 3. Initialize Model (Using your requested syntax)
            // Note: Ensure the library supporting this syntax is in your dependencies
            val generativeModel = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel("gemini-3-flash-preview")

            // 4. Create Prompt with JSON enforcement
            val prompt = """
                You are a Subscription Optimization Agent. Analyze these user subscriptions:
                
                $subListString
                
                Identify:
                1. Redundancies (e.g., multiple music apps).
                2. Bundle opportunities (e.g., Disney+ and Hulu).
                3. High cost warnings.
                
                Return ONLY a valid JSON list. Do not use Markdown formatting (no ```json).
                Structure:
                [
                  {
                    "type": "REDUNDANCY" | "BUNDLE" | "SUGGESTION",
                    "title": "Short Title",
                    "description": "Short explanation",
                    "savings": 0.0 (Numeric monthly savings estimate)
                  }
                ]
            """.trimIndent()

            // 5. Generate Content
            val response = generativeModel.generateContent(prompt)
            Log.d("AI", "AI Response: $response")
            val responseText = response.text ?: "[]"

            // Clean up potential markdown code blocks if the model ignores the instruction
            val cleanJson = responseText.replace("```json", "").replace("```", "").trim()

            // 6. Parse and Save
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
            // Return failure so WorkManager might retry later or log the error
            Result.failure()
        }
    }
}