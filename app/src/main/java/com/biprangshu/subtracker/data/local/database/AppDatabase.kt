package com.biprangshu.subtracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biprangshu.subtracker.data.local.dao.SubscriptionDao
import com.biprangshu.subtracker.data.local.entity.SubscriptionEntity

@Database(entities = [SubscriptionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun subscriptionDAO(): SubscriptionDao
}