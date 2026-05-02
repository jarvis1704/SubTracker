package com.biprangshu.subtracker.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import com.biprangshu.subtracker.core.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.core.worker.AIWorkerScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AISettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val aiWorkerScheduler: AIWorkerScheduler
) : ViewModel() {

    val isOptimizerEnabled = userPreferencesRepository.aiOptimizerEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isBurnRateEnabled = userPreferencesRepository.aiBurnRateEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isPriceAlertsEnabled = userPreferencesRepository.aiPriceAlertsEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val periodicityDays = userPreferencesRepository.aiPeriodicityFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 7)

    private fun updateWorkers() {
        aiWorkerScheduler.scheduleAIWorkers(
            optimizerEnabled = isOptimizerEnabled.value,
            burnRateEnabled = isBurnRateEnabled.value,
            priceAlertsEnabled = isPriceAlertsEnabled.value,
            periodDays = periodicityDays.value,
            policy = ExistingPeriodicWorkPolicy.UPDATE
        )
    }

    fun toggleOptimizer(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setAiOptimizerEnabled(enabled)
            updateWorkers()
        }
    }

    fun toggleBurnRate(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setAiBurnRateEnabled(enabled)
            updateWorkers()
        }
    }

    fun togglePriceAlerts(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setAiPriceAlertsEnabled(enabled)
            updateWorkers()
        }
    }

    fun setPeriodicity(days: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setAiPeriodicity(days)
            updateWorkers()
        }
    }
}
