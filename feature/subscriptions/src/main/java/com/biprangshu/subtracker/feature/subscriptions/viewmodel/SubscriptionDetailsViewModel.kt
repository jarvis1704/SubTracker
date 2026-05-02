package com.biprangshu.subtracker.feature.subscriptions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.core.data.local.dao.PriceAlertDao
import com.biprangshu.subtracker.core.data.local.entity.PriceAlertEntity
import com.biprangshu.subtracker.core.domain.model.Subscription
import com.biprangshu.subtracker.core.domain.model.User
import com.biprangshu.subtracker.core.domain.usecase.DeleteSubscriptionUseCase
import com.biprangshu.subtracker.core.domain.usecase.GetSubscriptionByIdUseCase
import com.biprangshu.subtracker.core.domain.usecase.GetUserDataUseCase
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
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val deleteSubscriptionUseCase: DeleteSubscriptionUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val priceAlertDao: PriceAlertDao
): ViewModel() {

    private val _subscriptionId = MutableStateFlow<Int?>(null)

    private val _subscription = MutableStateFlow<Subscription?>(null)
    val subscription: StateFlow<Subscription?> = _subscription.asStateFlow()

    val userData: StateFlow<User?> = getUserDataUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val priceAlert: StateFlow<PriceAlertEntity?> = _subscriptionId.flatMapLatest { id ->
        if (id != null) priceAlertDao.getAlertForSubscription(id) else flowOf(null)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun loadSubscription(id: Int){
        _subscriptionId.value = id
        viewModelScope.launch {
            _subscription.value = getSubscriptionByIdUseCase(id)
        }
    }

    fun deleteSubscription(onSuccess: () -> Unit) {
        val currentSub = _subscription.value ?: return
        viewModelScope.launch {
            deleteSubscriptionUseCase(currentSub)
            onSuccess()
        }
    }
}
