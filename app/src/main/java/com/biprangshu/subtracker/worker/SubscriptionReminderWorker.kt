package com.biprangshu.subtracker.worker

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class SubscriptionReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferencesRepository: UserPreferencesRepository
) : CoroutineWorker(context, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {

        val masterEnabled = userPreferencesRepository.notificationsEnabledFlow.first()
        if (!masterEnabled) return Result.success()

        val remindersEnabled = userPreferencesRepository.paymentRemindersEnabledFlow.first()
        if (!remindersEnabled) return Result.success()


        val subscriptionName = inputData.getString("name") ?: "Subscription"
        val price = inputData.getDouble("price", 0.0)
        val currency = inputData.getString("currency") ?: "$"
        val subscriptionId = inputData.getInt("id", 0)

        val message = "Your payment of $currency$price for $subscriptionName is coming up soon."

        NotificationHelper.showNotification(
            context = applicationContext,
            title = "Upcoming Payment: $subscriptionName",
            message = message,
            notificationId = subscriptionId
        )

        return Result.success()
    }
}