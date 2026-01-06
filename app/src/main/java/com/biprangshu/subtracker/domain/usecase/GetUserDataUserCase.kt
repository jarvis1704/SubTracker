package com.biprangshu.subtracker.domain.usecase

import com.biprangshu.subtracker.data.local.UserEntity
import com.biprangshu.subtracker.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUserCase @Inject constructor(
    private val repository: UserDataRepository
) {

    operator fun invoke(): Flow<UserEntity?> {
        return repository.getUser()
    }

}