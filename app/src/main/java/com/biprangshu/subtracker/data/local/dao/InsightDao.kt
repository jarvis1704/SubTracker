package com.biprangshu.subtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.biprangshu.subtracker.data.local.entity.InsightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InsightDao {
    @Query("SELECT * FROM insights ORDER BY timestamp DESC")
    fun getAllInsights(): Flow<List<InsightEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertInsights(insights: List<InsightEntity>)

    @Query("DELETE FROM insights")
    suspend fun clearInsights()
}