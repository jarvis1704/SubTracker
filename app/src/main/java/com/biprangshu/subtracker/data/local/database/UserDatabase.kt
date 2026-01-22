package com.biprangshu.subtracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biprangshu.subtracker.data.local.dao.UserDataDao
import com.biprangshu.subtracker.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDataDAO(): UserDataDao
}