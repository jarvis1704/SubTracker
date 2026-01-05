package com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.usecase.GetSubscriptionbyIdUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionDetailsViewModel @Inject constructor(
    private val getSubscriptionbyIdUsecase: GetSubscriptionbyIdUsecase
): ViewModel() {

    private val _subscription = MutableStateFlow<Subscription?>(null)
    val subscription: StateFlow<Subscription?> = _subscription.asStateFlow()

    fun loadSubscription(id: Int){
        viewModelScope.launch {
            _subscription.value = getSubscriptionbyIdUsecase(id)
        }
    }

}