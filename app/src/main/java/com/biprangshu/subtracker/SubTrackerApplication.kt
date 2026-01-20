package com.biprangshu.subtracker

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.worker.NotificationHelper
import com.biprangshu.subtracker.worker.SubOptimizerWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

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

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //todo: add settings to set priodicity by themselves, with 3 days as minimum
        val periodicRequest = PeriodicWorkRequestBuilder<SubOptimizerWorker>(7, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()


        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SubOptimizerPeriodic",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }
}