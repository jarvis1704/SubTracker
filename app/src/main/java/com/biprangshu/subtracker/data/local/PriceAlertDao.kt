package com.biprangshu.subtracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceAlertDao {
    @Query("SELECT * FROM price_alerts ORDER BY detectedAt DESC")
    fun getAllAlerts(): Flow<List<PriceAlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerts(alerts: List<PriceAlertEntity>)

    @Query("DELETE FROM price_alerts")
    suspend fun clearAlerts()

    @Delete
    suspend fun deleteAlert(alert: PriceAlertEntity)
}