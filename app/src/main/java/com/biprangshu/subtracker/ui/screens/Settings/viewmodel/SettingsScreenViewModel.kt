package com.biprangshu.subtracker.ui.screens.Settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.data.local.entity.UserEntity
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.domain.usecase.AddBudgetUseCase
import com.biprangshu.subtracker.domain.usecase.GetUserDataUserCase
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
    private val getUserDataUseCase: GetUserDataUserCase
): ViewModel() {

    val userData: StateFlow<UserEntity?> = getUserDataUseCase().stateIn(
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