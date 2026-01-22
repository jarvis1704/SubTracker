package com.biprangshu.subtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val currency: String = "$",

    //billing details
    val billingCycle: String, // e.g., "Monthly", "Yearly"
    val firstPaymentDate: Long, // Timestamp for calculations
    val paymentMethod: String? = null, // e.g., "Visa ending in 4567"

    //categorization
    val category: String = "Other",

    val iconName: String? = null,
    val colorHex: String? = null, // Store brand color like 0xFFE50914 for Netflix
    val logoResId: Int? = null,

    //reminders
    val remindersEnabled: Boolean = false,
    val reminderDaysBefore: Int = 1
)