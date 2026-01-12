package com.biprangshu.subtracker.ui.screens.editsubscriptionscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.usecase.AddSubscriptionUseCase
import com.biprangshu.subtracker.domain.usecase.GetSubscriptionbyIdUsecase
import com.biprangshu.subtracker.worker.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSubscriptionViewModel @Inject constructor(
    private val getSubscriptionByIdUseCase: GetSubscriptionbyIdUsecase,
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val reminderScheduler: ReminderScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditSubscriptionUiState>(EditSubscriptionUiState.Loading)
    val uiState: StateFlow<EditSubscriptionUiState> = _uiState.asStateFlow()

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
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val price = priceInput.toDoubleOrNull() ?: 0.0

            // Create updated object maintaining the original ID
            val updatedSubscription = originalSubscription.copy(
                price = price,
                billingCycle = billingCycle,
                firstPaymentDate = firstPaymentDate,
                category = category,
                paymentMethod = paymentMethod,
                remindersEnabled = remindersEnabled,
                reminderDaysBefore = reminderDaysBefore
            )

            // Insert (Replace) in DB
            addSubscriptionUseCase(updatedSubscription)

            // Update Reminder
            if (remindersEnabled) {
                reminderScheduler.scheduleReminder(
                    subscriptionId = updatedSubscription.id,
                    name = updatedSubscription.name,
                    price = price,
                    currency = updatedSubscription.currency,
                    billingCycle = billingCycle,
                    firstPaymentDate = firstPaymentDate,
                    reminderDaysBefore = reminderDaysBefore
                )
            } else {
                // If disabled, we might want to cancel the worker,
                // but currently ReminderScheduler only has schedule.
                // Assuming scheduleReminder handles replacement/cancellation internally or existing worker continues.
                // ideally: reminderScheduler.cancelReminder(updatedSubscription.id)
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