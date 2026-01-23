package com.biprangshu.subtracker

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.worker.BurnRateWorker
import com.biprangshu.subtracker.worker.NotificationHelper
import com.biprangshu.subtracker.worker.PriceIncreaseWorker
import com.biprangshu.subtracker.worker.SubOptimizerWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class SubTrackerApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createNotificationChannel(this)


        CoroutineScope(Dispatchers.IO).launch {
            showOnboardingScreens = userPreferencesRepository.isFirstLaunchFlow.first()
            isAppReady = true
        }


        CoroutineScope(Dispatchers.Default).launch {
            combine(
                userPreferencesRepository.aiOptimizerEnabledFlow,
                userPreferencesRepository.aiBurnRateEnabledFlow,
                userPreferencesRepository.aiPriceAlertsEnabledFlow,
                userPreferencesRepository.aiPeriodicityFlow
            ) { optimizerEnabled, burnRateEnabled, priceAlertsEnabled, periodicityDays ->
                scheduleAIWorkers(optimizerEnabled, burnRateEnabled, priceAlertsEnabled, periodicityDays)
            }.collect()
        }
    }

    private fun scheduleAIWorkers(
        optimizerEnabled: Boolean,
        burnRateEnabled: Boolean,
        priceAlertsEnabled: Boolean,
        periodDays: Int
    ) {
        val workManager = WorkManager.getInstance(this)


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
                val request = PeriodicWorkRequest.Builder(workerClass, safePeriod, TimeUnit.DAYS,)
                    .setConstraints(constraints)
                    .setInitialDelay(delayDays, TimeUnit.DAYS)
                    .addTag(tag)
                    .build()

                workManager.enqueueUniquePeriodicWork(
                    tag,
                    ExistingPeriodicWorkPolicy.KEEP,
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