package com.biprangshu.subtracker.ui.screens.AnalyticsScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.data.local.UserEntity
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.domain.usecase.GetUserDataUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisScreenViewModel @Inject constructor(
    private val userDataUserCase: dagger.Lazy<GetUserDataUserCase>,
    private val subscriptionRepository: SubscriptionRepository
): ViewModel() {

    //todo: implement viewmodel logic for Analytics Screen

    val userData: StateFlow<UserEntity?> = userDataUserCase.get().invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val totalMonthlySpend: StateFlow<Double> = subscriptionRepository.getTotalMonthlySpend()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )


}