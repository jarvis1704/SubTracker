package com.biprangshu.subtracker.core.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Subscription(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val currency: String,
    val iconName: String?,
    val logoRedId: Int?,
    val colorHex: String? = null,
    val billingCycle: String,
    val firstPaymentDate: Long,
    val paymentMethod: String?,
    val category: String,
    val remindersEnabled: Boolean,
    val reminderDaysBefore: Int,


    val nextPaymentDate: Long = 0,
    val dueInDays: Int = 0
)
