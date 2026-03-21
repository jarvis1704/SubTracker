package com.biprangshu.subtracker.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.core.domain.model.User
import com.biprangshu.subtracker.core.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.core.domain.usecase.AddBudgetUseCase
import com.biprangshu.subtracker.core.domain.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val budgetUseCase: AddBudgetUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
): ViewModel() {

    val userData: StateFlow<User?> = getUserDataUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    val isBiometricEnabled: StateFlow<Boolean> = userPreferencesRepository.isBiometricEnabledFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = false
        )

    fun toggleBiometric(enabled: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.setBiometricEnabled(enabled)
        }
    }

    fun addBudget(amount: Double, category: String){
        viewModelScope.launch {
            budgetUseCase(amount, category)
        }
    }

}
