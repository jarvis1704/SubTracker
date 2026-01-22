package com.biprangshu.subtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.biprangshu.subtracker.data.local.entity.PriceAlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceAlertDao {
    @Query("SELECT * FROM price_alerts ORDER BY detectedAt DESC")
    fun getAllAlerts(): Flow<List<PriceAlertEntity>>

    @Query("SELECT * FROM price_alerts WHERE subscriptionId = :subId LIMIT 1")
    fun getAlertForSubscription(subId: Int): Flow<PriceAlertEntity?>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAlerts(alerts: List<PriceAlertEntity>)

    @Query("DELETE FROM price_alerts")
    suspend fun clearAlerts()

    @Delete
    suspend fun deleteAlert(alert: PriceAlertEntity)
}