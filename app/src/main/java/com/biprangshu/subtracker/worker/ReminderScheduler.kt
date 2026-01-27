package com.biprangshu.subtracker.worker

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun scheduleReminder(
        subscriptionId: Int,
        name: String,
        price: Double,
        currency: String,
        billingCycle: String,
        firstPaymentDate: Long,
        reminderDaysBefore: Int
    ) {
        val nextPaymentTime = calculateNextPaymentTimestamp(firstPaymentDate, billingCycle)
        val reminderTime = nextPaymentTime - TimeUnit.DAYS.toMillis(reminderDaysBefore.toLong())
        val currentTime = System.currentTimeMillis()


        var finalTriggerTime = reminderTime
        if (finalTriggerTime <= currentTime) {
            val cal = Calendar.getInstance()
            cal.timeInMillis = nextPaymentTime
            if (billingCycle.equals("Monthly", ignoreCase = true)) {
                cal.add(Calendar.MONTH, 1)
            } else {
                cal.add(Calendar.YEAR, 1)
            }
            finalTriggerTime = cal.timeInMillis - TimeUnit.DAYS.toMillis(reminderDaysBefore.toLong())
        }

        val delay = finalTriggerTime - currentTime
        if (delay <= 0) return

        val data = Data.Builder()
            .putInt("id", subscriptionId)
            .putString("name", name)
            .putDouble("price", price)
            .putString("currency", currency)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SubscriptionReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("sub_$subscriptionId")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "reminder_$subscriptionId",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    private fun calculateNextPaymentTimestamp(startDate: Long, cycle: String): Long {
        val calendar = Calendar.getInstance()
        val current = Calendar.getInstance()
        calendar.timeInMillis = startDate


        if (calendar.after(current)) {
            return calendar.timeInMillis
        }


        while (!calendar.after(current)) {
            if (cycle.equals("Monthly", ignoreCase = true)) {
                calendar.add(Calendar.MONTH, 1)
            } else {
                calendar.add(Calendar.YEAR, 1)
            }
        }
        return calendar.timeInMillis
    }

    fun cancelReminder(subscriptionId: Int) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag("sub_$subscriptionId")
        workManager.cancelUniqueWork("reminder_$subscriptionId")
    }
}