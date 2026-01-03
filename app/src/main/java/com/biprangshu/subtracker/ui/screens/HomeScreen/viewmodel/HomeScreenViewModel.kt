package com.biprangshu.subtracker.ui.screens.HomeScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.domain.usecase.GetSortedSubscriptionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSortedSubscriptionsUseCase: GetSortedSubscriptionsUseCase,
    subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    // Hot flow of subscriptions, sorted by due date
    val subscriptions: StateFlow<List<Subscription>> = getSortedSubscriptionsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Hot flow of the total monthly spend
    val totalMonthlySpend: StateFlow<Double> = subscriptionRepository.getTotalMonthlySpend()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )
}