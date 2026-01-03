package com.biprangshu.subtracker.domain.model

data class Subscription(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val currency: String,
    val iconName: String?,
    val logoRedId: Int?,
    val colorHex: String?,
    val billingCycle: String,
    val firstPaymentDate: Long,


    val nextPaymentDate: Long = 0,
    val dueInDays: Int = 0
)
