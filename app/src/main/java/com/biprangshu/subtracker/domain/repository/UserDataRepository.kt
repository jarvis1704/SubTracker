package com.biprangshu.subtracker.domain.repository

import com.biprangshu.subtracker.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    fun getUser(): Flow<UserEntity?>


    suspend fun setUserData(userEntity: UserEntity)


    suspend fun updateBudget(budget: Double, currency: String)
}