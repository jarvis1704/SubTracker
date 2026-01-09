package com.biprangshu.subtracker.worker

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.Worker
import androidx.work.WorkerParameters

class SubscriptionReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
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

        // Todo: Ideally reschedule the next periodic work here if doing one-time chain,
        // but strictly PeriodicWorkRequest is better for recurring.
        // For this implementation, we handle the single upcoming instance.

        return Result.success()
    }
}