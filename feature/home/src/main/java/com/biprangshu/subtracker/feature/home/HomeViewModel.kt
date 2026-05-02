package com.biprangshu.subtracker.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.core.domain.model.Subscription
import com.biprangshu.subtracker.core.domain.model.User
import com.biprangshu.subtracker.core.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.core.domain.usecase.GetSortedSubscriptionsUseCase
import com.biprangshu.subtracker.core.domain.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSortedSubscriptionsUseCase: GetSortedSubscriptionsUseCase,
    subscriptionRepository: SubscriptionRepository,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {

    val userData: StateFlow<User?> = getUserDataUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val subscriptions: StateFlow<List<Subscription>> = getSortedSubscriptionsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalMonthlySpend: StateFlow<Double> = subscriptionRepository.getTotalMonthlySpend()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )
}
