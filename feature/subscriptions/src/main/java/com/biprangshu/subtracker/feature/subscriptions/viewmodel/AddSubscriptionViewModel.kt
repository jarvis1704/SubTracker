package com.biprangshu.subtracker.feature.subscriptions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.core.domain.model.Subscription
import com.biprangshu.subtracker.core.domain.model.User
import com.biprangshu.subtracker.core.domain.repository.ReminderSchedulerContract
import com.biprangshu.subtracker.core.domain.usecase.AddSubscriptionUseCase
import com.biprangshu.subtracker.core.domain.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSubscriptionViewModel @Inject constructor(
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val reminderScheduler: ReminderSchedulerContract,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {

    val userData: StateFlow<User?> = getUserDataUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun saveSubscription(
        name: String,
        priceInput: String,
        billingCycle: String,
        firstPaymentDate: Long,
        category: String,
        paymentMethod: String,
        iconResId: Int,
        iconName: String,
        reminderEnabled: Boolean,
        reminderDaysBefore: Int,
        color: Long,
        isTrial: Boolean = false,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val price = priceInput.toDoubleOrNull() ?: 0.0
            val colorHex = String.format("#%08X", color)

            val subscription = Subscription(
                name = name,
                price = price,
                currency = "$",
                billingCycle = billingCycle,
                firstPaymentDate = firstPaymentDate,
                category = category,
                paymentMethod = paymentMethod,
                colorHex = colorHex,
                logoRedId = iconResId,
                iconName = iconName,
                remindersEnabled = reminderEnabled,
                reminderDaysBefore = reminderDaysBefore,
                isTrial = isTrial
            )

            val newId = try {
                addSubscriptionUseCase(subscription)
            } catch (e: IllegalArgumentException) {
                -1L
            }

            if (newId != -1L) {
                if (reminderEnabled) {
                    reminderScheduler.scheduleReminder(
                        subscriptionId = newId.toInt(),
                        name = name,
                        price = price,
                        currency = "$",
                        billingCycle = billingCycle,
                        firstPaymentDate = firstPaymentDate,
                        reminderDaysBefore = reminderDaysBefore,
                        isTrial = isTrial
                    )
                }
                onSuccess()
            }
        }
    }
}
