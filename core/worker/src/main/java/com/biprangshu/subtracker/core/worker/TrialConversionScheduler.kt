package com.biprangshu.subtracker.core.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Scheduler for the TrialConversionWorker.
 * 
 * Schedules a periodic background task that converts ended trial subscriptions
 * to regular subscriptions. The worker runs once daily to ensure data consistency.
 */
@Singleton
class TrialConversionScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val WORK_NAME = "TrialConversionPeriodic"
        private const val WORK_TAG = "TrialConversionWorker"
        private const val PERIOD_HOURS = 24L
        private const val INITIAL_DELAY_MINUTES = 5L
    }

    /**
     * Schedules the trial conversion worker to run periodically.
     * 
     * The worker will:
     * - Run once every 24 hours
     * - Start with a 5-minute initial delay (to not block app startup)
     * - Use KEEP policy to avoid duplicate scheduling
     */
    fun scheduleTrialConversion() {
        val request = PeriodicWorkRequest.Builder(
            TrialConversionWorker::class.java,
            PERIOD_HOURS, TimeUnit.HOURS
        )
            .setInitialDelay(INITIAL_DELAY_MINUTES, TimeUnit.MINUTES)
            .addTag(WORK_TAG)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
