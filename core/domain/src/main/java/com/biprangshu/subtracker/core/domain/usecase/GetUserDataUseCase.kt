package com.biprangshu.subtracker.core.domain.usecase

import com.biprangshu.subtracker.core.domain.model.User
import com.biprangshu.subtracker.core.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: UserDataRepository
) {

    operator fun invoke(): Flow<User?> {
        return repository.getUser()
    }

}
