package com.biprangshu.subtracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biprangshu.subtracker.data.local.dao.InsightDao
import com.biprangshu.subtracker.data.local.entity.InsightEntity

@Database(entities = [InsightEntity::class], version = 1, exportSchema = false)
abstract class InsightDatabase : RoomDatabase(){
    abstract fun insightDAO(): InsightDao
}