package com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.data.local.PriceAlertDao
import com.biprangshu.subtracker.data.local.PriceAlertEntity
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.usecase.GetSubscriptionbyIdUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionDetailsViewModel @Inject constructor(
    private val getSubscriptionbyIdUsecase: GetSubscriptionbyIdUsecase,
    private val priceAlertDao: PriceAlertDao
): ViewModel() {

    private val _subscriptionId = MutableStateFlow<Int?>(null)

    private val _subscription = MutableStateFlow<Subscription?>(null)
    val subscription: StateFlow<Subscription?> = _subscription.asStateFlow()


    val priceAlert: StateFlow<PriceAlertEntity?> = _subscriptionId.flatMapLatest { id ->
        if (id != null) priceAlertDao.getAlertForSubscription(id) else flowOf(null)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun loadSubscription(id: Int){
        _subscriptionId.value = id
        viewModelScope.launch {
            _subscription.value = getSubscriptionbyIdUsecase(id)
        }
    }
}