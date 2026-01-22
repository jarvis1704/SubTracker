package com.biprangshu.subtracker.data.local.repository

import com.biprangshu.subtracker.data.local.dao.UserDataDao
import com.biprangshu.subtracker.data.local.entity.UserEntity
import com.biprangshu.subtracker.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val dao: UserDataDao
): UserDataRepository {

    override fun getUser(): Flow<UserEntity?> {
        return dao.getUserData()
    }

    override suspend fun setUserData(userEntity: UserEntity) {

        val singleUser = userEntity.copy(id = 1)
        dao.insertUserData(singleUser)
    }


    override suspend fun updateBudget(budget: Double, currency: String) {
        val currentUser = dao.getUserData().firstOrNull() ?: UserEntity(
            id = 1,
            preferredCurrency = currency,
            budget = budget
        )

        val updatedUser = currentUser.copy(
            id = 1,
            budget = budget,
            preferredCurrency = currency
        )

        dao.insertUserData(updatedUser)
    }

}