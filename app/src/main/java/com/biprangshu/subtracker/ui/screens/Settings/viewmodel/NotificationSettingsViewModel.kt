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
class NotificationSettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val isMasterEnabled = userPreferencesRepository.notificationsEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isPaymentRemindersEnabled = userPreferencesRepository.paymentRemindersEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)


    fun toggleMaster(enabled: Boolean) {
        viewModelScope.launch { userPreferencesRepository.setNotificationsEnabled(enabled) }
    }

    fun togglePaymentReminders(enabled: Boolean) {
        viewModelScope.launch { userPreferencesRepository.setPaymentRemindersEnabled(enabled) }
    }
}