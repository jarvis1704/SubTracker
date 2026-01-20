package com.biprangshu.subtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [InsightEntity::class], version = 1, exportSchema = false)
abstract class InsightDatabase : RoomDatabase(){
    abstract fun insightDAO(): InsightDao
}