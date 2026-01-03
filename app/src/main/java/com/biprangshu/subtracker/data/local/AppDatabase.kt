package com.biprangshu.subtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SubscriptionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun subscriptionDAO(): SubscriptionDao
}