package com.biprangshu.subtracker.ui.screens.Settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AISettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val isOptimizerEnabled = userPreferencesRepository.aiOptimizerEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isBurnRateEnabled = userPreferencesRepository.aiBurnRateEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isPriceAlertsEnabled = userPreferencesRepository.aiPriceAlertsEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val periodicityDays = userPreferencesRepository.aiPeriodicityFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 7)

    fun toggleOptimizer(enabled: Boolean) {
        viewModelScope.launch { userPreferencesRepository.setAiOptimizerEnabled(enabled) }
    }

    fun toggleBurnRate(enabled: Boolean) {
        viewModelScope.launch { userPreferencesRepository.setAiBurnRateEnabled(enabled) }
    }

    fun togglePriceAlerts(enabled: Boolean) {
        viewModelScope.launch { userPreferencesRepository.setAiPriceAlertsEnabled(enabled) }
    }

    fun setPeriodicity(days: Int) {
        viewModelScope.launch { userPreferencesRepository.setAiPeriodicity(days) }
    }
}