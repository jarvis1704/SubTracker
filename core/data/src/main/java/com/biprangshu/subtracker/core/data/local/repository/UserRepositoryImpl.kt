package com.biprangshu.subtracker.core.data.local.repository

import com.biprangshu.subtracker.core.data.local.dao.UserDataDao
import com.biprangshu.subtracker.core.data.local.entity.UserEntity
import com.biprangshu.subtracker.core.domain.model.User
import com.biprangshu.subtracker.core.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDataDao
) : UserDataRepository {

    override fun getUser(): Flow<User?> {
        return dao.getUserData().map { it?.toDomain() }
    }

    override suspend fun setUserData(user: User) {
        val singleUser = user.toEntity().copy(id = 1)
        dao.insertUserData(singleUser)
    }

    override suspend fun updateBudget(budget: Double, currency: String) {
        val currentEntity = dao.getUserData().firstOrNull() ?: UserEntity(
            id = 1,
            preferredCurrency = currency,
            budget = budget
        )

        val updatedEntity = currentEntity.copy(
            id = 1,
            budget = budget,
            preferredCurrency = currency
        )

        dao.insertUserData(updatedEntity)
    }

    private fun UserEntity.toDomain(): User {
        return User(
            id = id,
            name = name,
            email = email,
            preferredCurrency = preferredCurrency,
            budget = budget
        )
    }

    private fun User.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            email = email,
            preferredCurrency = preferredCurrency,
            budget = budget
        )
    }
}
