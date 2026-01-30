package com.biprangshu.subtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val month: String,
    val year: Int,
    val predictedSpend: Double,
    val averageSpend: Double,
    val timestamp: Long = System.currentTimeMillis()
)