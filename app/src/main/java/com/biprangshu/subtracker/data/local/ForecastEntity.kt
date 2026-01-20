package com.biprangshu.subtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val month: String, // e.g., "Feb"
    val year: Int,     // e.g., 2026
    val predictedSpend: Double, // Actual cash flow needed (includes spikes)
    val averageSpend: Double,   // Smoothed average (Monthly + Yearly/12)
    val timestamp: Long = System.currentTimeMillis()
)