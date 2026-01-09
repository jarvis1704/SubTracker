package com.biprangshu.subtracker

import android.app.Application
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.worker.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltAndroidApp
class SubTrackerApplication : Application() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createNotificationChannel(this)


        CoroutineScope(Dispatchers.IO).launch {
            showOnboardingScreens = userPreferencesRepository.isFirstLaunchFlow.first()

            isAppReady = true
        }
    }


}