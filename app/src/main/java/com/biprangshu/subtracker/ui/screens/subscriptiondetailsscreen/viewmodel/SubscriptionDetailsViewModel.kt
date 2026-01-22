package com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.data.local.PriceAlertDao
import com.biprangshu.subtracker.data.local.PriceAlertEntity
import com.biprangshu.subtracker.data.local.UserEntity
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.usecase.DeleteSubscriptionUseCase
import com.biprangshu.subtracker.domain.usecase.GetSubscriptionbyIdUsecase
import com.biprangshu.subtracker.domain.usecase.GetUserDataUserCase
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
    private val deleteSubscriptionUseCase: DeleteSubscriptionUseCase,
    private val userDataUserCase: GetUserDataUserCase,
    private val priceAlertDao: PriceAlertDao
): ViewModel() {

    private val _subscriptionId = MutableStateFlow<Int?>(null)

    private val _subscription = MutableStateFlow<Subscription?>(null)
    val subscription: StateFlow<Subscription?> = _subscription.asStateFlow()

    val userData: StateFlow<UserEntity?> = userDataUserCase().stateIn(
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
            _subscription.value = getSubscriptionbyIdUsecase(id)
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