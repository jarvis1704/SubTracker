package com.biprangshu.subtracker.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biprangshu.subtracker.core.data.local.dao.PriceAlertDao
import com.biprangshu.subtracker.core.data.local.entity.PriceAlertEntity

@Database(entities = [PriceAlertEntity::class], version = 1, exportSchema = false)
abstract class PriceAlertDatabase : RoomDatabase() {
    abstract fun priceAlertDao(): PriceAlertDao
}
