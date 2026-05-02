package com.biprangshu.subtracker.feature.subscriptions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.core.domain.model.Subscription
import com.biprangshu.subtracker.core.domain.model.User
import com.biprangshu.subtracker.core.domain.repository.ReminderSchedulerContract
import com.biprangshu.subtracker.core.domain.usecase.AddSubscriptionUseCase
import com.biprangshu.subtracker.core.domain.usecase.GetSubscriptionByIdUseCase
import com.biprangshu.subtracker.core.domain.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSubscriptionViewModel @Inject constructor(
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val reminderScheduler: ReminderSchedulerContract
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditSubscriptionUiState>(EditSubscriptionUiState.Loading)
    val uiState: StateFlow<EditSubscriptionUiState> = _uiState.asStateFlow()

    val userData: StateFlow<User?> = getUserDataUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun loadSubscription(id: Int) {
        viewModelScope.launch {
            val subscription = getSubscriptionByIdUseCase(id)
            if (subscription != null) {
                _uiState.value = EditSubscriptionUiState.Success(subscription)
            } else {
                _uiState.value = EditSubscriptionUiState.Error
            }
        }
    }

    fun updateSubscription(
        originalSubscription: Subscription,
        priceInput: String,
        billingCycle: String,
        firstPaymentDate: Long,
        category: String,
        paymentMethod: String,
        remindersEnabled: Boolean,
        reminderDaysBefore: Int,
        isTrial: Boolean = false,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val price = priceInput.toDoubleOrNull() ?: 0.0

            val updatedSubscription = originalSubscription.copy(
                price = price,
                billingCycle = billingCycle,
                firstPaymentDate = firstPaymentDate,
                category = category,
                paymentMethod = paymentMethod,
                remindersEnabled = remindersEnabled,
                reminderDaysBefore = reminderDaysBefore,
                isTrial = isTrial
            )

            addSubscriptionUseCase(updatedSubscription)

            if (remindersEnabled) {
                reminderScheduler.scheduleReminder(
                    subscriptionId = updatedSubscription.id,
                    name = updatedSubscription.name,
                    price = price,
                    currency = updatedSubscription.currency,
                    billingCycle = billingCycle,
                    firstPaymentDate = firstPaymentDate,
                    reminderDaysBefore = reminderDaysBefore,
                    isTrial = isTrial
                )
            } else {
                reminderScheduler.cancelReminder(updatedSubscription.id)
            }

            onSuccess()
        }
    }
}

sealed interface EditSubscriptionUiState {
    data object Loading : EditSubscriptionUiState
    data object Error : EditSubscriptionUiState
    data class Success(val subscription: Subscription) : EditSubscriptionUiState
}
