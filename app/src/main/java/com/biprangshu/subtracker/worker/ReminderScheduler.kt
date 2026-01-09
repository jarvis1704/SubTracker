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

        // If the calculated reminder time is in the past, move to the next cycle
        // (Simple logic: if reminder time < now, add one cycle)
        var finalTriggerTime = reminderTime
        if (finalTriggerTime <= currentTime) {
            val cycleDuration = if (billingCycle.equals("Monthly", ignoreCase = true)) {
                30L // Approx
            } else {
                365L // Approx
            }
            // Recalculate based on next cycle for better accuracy
            // For production, use Calendar/LocalDate logic properly for cycle addition
            finalTriggerTime = calculateNextPaymentTimestamp(nextPaymentTime, billingCycle) - TimeUnit.DAYS.toMillis(reminderDaysBefore.toLong())
        }

        val delay = finalTriggerTime - currentTime
        if (delay <= 0) return // Should not happen with above logic, but safety check

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

        // If start date is in future, that's the next payment
        if (calendar.after(current)) {
            return calendar.timeInMillis
        }

        // Loop adding cycles until we find the next future date
        while (!calendar.after(current)) {
            if (cycle.equals("Monthly", ignoreCase = true)) {
                calendar.add(Calendar.MONTH, 1)
            } else {
                calendar.add(Calendar.YEAR, 1)
            }
        }
        return calendar.timeInMillis
    }
}