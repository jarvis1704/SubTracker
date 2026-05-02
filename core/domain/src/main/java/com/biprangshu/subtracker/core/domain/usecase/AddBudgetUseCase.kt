package com.biprangshu.subtracker.core.domain.usecase

import com.biprangshu.subtracker.core.domain.repository.UserDataRepository
import javax.inject.Inject

class AddBudgetUseCase @Inject constructor(
    private val repository: UserDataRepository
) {

    suspend operator fun invoke(budget: Double, currency: String) {
        repository.updateBudget(budget, currency)
    }


}
