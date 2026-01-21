package com.biprangshu.subtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price_alerts")
data class PriceAlertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subscriptionName: String,
    val subscriptionId: Int,
    val oldPrice: Double,
    val newPrice: Double,
    val currency: String,
    val message: String,
    val detectedAt: Long = System.currentTimeMillis()
)