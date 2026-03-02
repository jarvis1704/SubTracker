package com.biprangshu.subtracker.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class AIWorkerScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun scheduleAIWorkers(
        optimizerEnabled: Boolean,
        burnRateEnabled: Boolean,
        priceAlertsEnabled: Boolean,
        periodDays: Int,
        policy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP
    ) {
        val workManager = WorkManager.getInstance(context)

        val safePeriod = periodDays.coerceIn(3, 7).toLong()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        fun manageWorker(
            isEnabled: Boolean,
            tag: String,
            workerClass: Class<out androidx.work.ListenableWorker>,
            delayDays: Long
        ) {
            if (isEnabled) {
                val request = PeriodicWorkRequest.Builder(workerClass, safePeriod, TimeUnit.DAYS)
                    .setConstraints(constraints)
                    .setInitialDelay(delayDays, TimeUnit.DAYS)
                    .addTag(tag)
                    .build()

                workManager.enqueueUniquePeriodicWork(
                    tag,
                    policy,
                    request
                )
            } else {
                workManager.cancelUniqueWork(tag)
            }
        }

        manageWorker(optimizerEnabled, "SubOptimizerPeriodic", SubOptimizerWorker::class.java, 0)
        manageWorker(burnRateEnabled, "BurnRatePeriodic", BurnRateWorker::class.java, 2)
        manageWorker(priceAlertsEnabled, "PriceAlertPeriodic", PriceIncreaseWorker::class.java, 4)
    }
}