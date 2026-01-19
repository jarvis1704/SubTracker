package com.biprangshu.subtracker.ui.screens.addsubscriptionscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.usecase.AddSubscriptionUseCase
import com.biprangshu.subtracker.worker.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSubscriptionViewModel @Inject constructor(
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val reminderScheduler: ReminderScheduler
) : ViewModel() {

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
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            // 1. Validate & Parse Price
            val price = priceInput.toDoubleOrNull() ?: 0.0

            val colorHex = String.format("#%08X", color)

            // 2. Create Domain Model
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
                reminderDaysBefore = reminderDaysBefore

            )


            val newId = addSubscriptionUseCase(subscription)


            if (reminderEnabled && newId != -1L) {
                reminderScheduler.scheduleReminder(
                    subscriptionId = newId.toInt(),
                    name = name,
                    price = price,
                    currency = "$",
                    billingCycle = billingCycle,
                    firstPaymentDate = firstPaymentDate,
                    reminderDaysBefore = reminderDaysBefore
                )
            }


            onSuccess()
        }
    }
}