package com.biprangshu.subtracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biprangshu.subtracker.data.local.dao.ForecastDao
import com.biprangshu.subtracker.data.local.entity.ForecastEntity

@Database(entities = [ForecastEntity::class], version = 1, exportSchema = false)
abstract class ForecastDatabase : RoomDatabase(){
    abstract fun forecastDAO(): ForecastDao
}