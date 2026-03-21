package com.biprangshu.subtracker.core.domain.repository

interface ReminderSchedulerContract {
    fun scheduleReminder(
        subscriptionId: Int,
        name: String,
        price: Double,
        currency: String,
        billingCycle: String,
        firstPaymentDate: Long,
        reminderDaysBefore: Int
    )
    fun cancelReminder(subscriptionId: Int)
}
