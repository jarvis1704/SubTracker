package com.biprangshu.subtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PriceAlertEntity::class], version = 1, exportSchema = false)
abstract class PriceAlertDatabase : RoomDatabase(){
    abstract fun priceAlertDao(): PriceAlertDao
}