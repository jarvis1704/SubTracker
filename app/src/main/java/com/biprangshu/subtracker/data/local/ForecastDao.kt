package com.biprangshu.subtracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecasts ORDER BY year ASC, id ASC")
    fun getForecasts(): Flow<List<ForecastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(forecasts: List<ForecastEntity>)

    @Query("DELETE FROM forecasts")
    suspend fun clearForecasts()
}