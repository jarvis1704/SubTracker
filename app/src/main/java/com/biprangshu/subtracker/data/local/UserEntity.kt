package com.biprangshu.subtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val email: String? = null,
    val preferredCurrency: String = "$",
    val budget: Double,
)
