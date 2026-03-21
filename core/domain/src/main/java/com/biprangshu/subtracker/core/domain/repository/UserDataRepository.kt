package com.biprangshu.subtracker.core.domain.repository

import com.biprangshu.subtracker.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    fun getUser(): Flow<User?>
    suspend fun setUserData(user: User)
    suspend fun updateBudget(budget: Double, currency: String)
}
