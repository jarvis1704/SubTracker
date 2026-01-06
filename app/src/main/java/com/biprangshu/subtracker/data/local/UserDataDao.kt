package com.biprangshu.subtracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao{

    @Query("SELECT * FROM user_data LIMIT 1")
    fun getUserData(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userData: UserEntity)

    @Delete
    suspend fun deleteUserData(userData: UserEntity)

    @Update
    suspend fun updateUserData(userData: UserEntity)
}
