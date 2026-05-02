package com.biprangshu.subtracker.core.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.biprangshu.subtracker.core.domain.repository.SubscriptionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Background worker that automatically converts ended trial subscriptions
 * to regular subscriptions by setting isTrial = false in the database.
 *
 * This worker runs periodically (once daily) to ensure data consistency
 * and clean up trials that have naturally expired.
 */
@HiltWorker
class TrialConversionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val subscriptionRepository: SubscriptionRepository
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "TrialConversionWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            val convertedCount = subscriptionRepository.convertEndedTrials()
            if (convertedCount > 0) {
                Log.d(TAG, "Converted $convertedCount ended trial(s) to regular subscription(s)")
            }
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error converting ended trials", e)
            Result.retry()
        }
    }
}
