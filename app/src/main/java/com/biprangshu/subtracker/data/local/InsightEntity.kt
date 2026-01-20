package com.biprangshu.subtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "insights")
data class InsightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String, // "REDUNDANCY", "BUNDLE", "OPPORTUNITY"
    val title: String,
    val description: String,
    val potentialSavings: Double,
    val timestamp: Long = System.currentTimeMillis()
)