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
    val billingCycle: String,
    val firstPaymentDate: Long,
    val paymentMethod: String? = null,

    //categorization
    val category: String = "Other",

    val iconName: String? = null,
    val colorHex: String? = null,
    val logoResId: Int? = null,

    //reminders
    val remindersEnabled: Boolean = false,
    val reminderDaysBefore: Int = 1
)