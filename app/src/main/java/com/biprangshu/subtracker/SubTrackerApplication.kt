package com.biprangshu.subtracker

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.worker.AIWorkerScheduler
import com.biprangshu.subtracker.worker.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SubTrackerApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    @Inject
    lateinit var aiWorkerScheduler: AIWorkerScheduler

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
            
            // Schedule workers once on startup based on current preferences
            val optimizerEnabled = userPreferencesRepository.aiOptimizerEnabledFlow.first()
            val burnRateEnabled = userPreferencesRepository.aiBurnRateEnabledFlow.first()
            val priceAlertsEnabled = userPreferencesRepository.aiPriceAlertsEnabledFlow.first()
            val periodDays = userPreferencesRepository.aiPeriodicityFlow.first()
            
            aiWorkerScheduler.scheduleAIWorkers(
                optimizerEnabled, 
                burnRateEnabled, 
                priceAlertsEnabled, 
                periodDays
            )
        }
    }
}