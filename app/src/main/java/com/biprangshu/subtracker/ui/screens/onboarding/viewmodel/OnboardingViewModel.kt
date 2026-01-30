package com.biprangshu.subtracker.ui.screens.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.domain.usecase.AddBudgetUseCase
import com.biprangshu.subtracker.isAppReady
import com.biprangshu.subtracker.showOnboardingScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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