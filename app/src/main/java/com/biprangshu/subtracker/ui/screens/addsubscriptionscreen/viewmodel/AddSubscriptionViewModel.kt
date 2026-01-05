package com.biprangshu.subtracker.ui.screens.addsubscriptionscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.usecase.AddSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSubscriptionViewModel @Inject constructor(
    private val addSubscriptionUseCase: AddSubscriptionUseCase
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
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            // 1. Validate & Parse Price
            val price = priceInput.toDoubleOrNull() ?: 0.0

            // 2. Create Domain Model
            val subscription = Subscription(
                name = name,
                price = price,
                currency = "$", // You can make this dynamic later
                billingCycle = billingCycle,
                firstPaymentDate = firstPaymentDate,
                category = category, // Ensure your Domain Model has this field!*
                paymentMethod = paymentMethod, // Ensure your Domain Model has this field!*

                // Store the resource ID as a String for now
                logoRedId = iconResId,
                iconName = iconName

            )

            // 3. Save to DB
            addSubscriptionUseCase(subscription)

            // 4. Navigate back
            onSuccess()
        }
    }
}