package com.biprangshu.subtracker.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biprangshu.subtracker.core.data.local.dao.SubscriptionDao
import com.biprangshu.subtracker.core.data.local.entity.SubscriptionEntity

@Database(entities = [SubscriptionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun subscriptionDAO(): SubscriptionDao
}
