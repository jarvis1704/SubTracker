package com.biprangshu.subtracker.ui.screens.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.showOnboardingScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    init {
        showOnboarding()
    }

    fun showOnboarding(){
        viewModelScope.launch {

            showOnboardingScreens = userPreferencesRepository.isFirstLaunchFlow.first()
        }
    }

    fun updateFirstAppOpen(appOpen: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setFirstLaunch(appOpen)
        }
    }




}