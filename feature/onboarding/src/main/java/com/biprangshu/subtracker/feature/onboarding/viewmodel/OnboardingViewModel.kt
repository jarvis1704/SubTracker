package com.biprangshu.subtracker.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.core.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.core.domain.usecase.AddBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val addBudgetUseCase: dagger.Lazy<AddBudgetUseCase>
): ViewModel() {

    fun updateFirstAppOpen(appOpen: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setFirstLaunch(appOpen)
        }
    }

    //tester to remove all userpreferences
    fun removeUserPreference(){
        viewModelScope.launch {
            userPreferencesRepository.clearUserData()
        }
    }

    fun saveBudget(budget: Double, currency: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            addBudgetUseCase.get().invoke(budget, currency)
            onSuccess()
        }
    }
}
