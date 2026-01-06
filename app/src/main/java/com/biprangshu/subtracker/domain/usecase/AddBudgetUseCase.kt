package com.biprangshu.subtracker.domain.usecase

import com.biprangshu.subtracker.domain.repository.UserDataRepository
import javax.inject.Inject

class AddBudgetUseCase @Inject constructor(
    private val repository: UserDataRepository
) {

    suspend operator fun invoke(budget: Double, currency: String) {
        repository.updateBudget(budget, currency)
    }


}