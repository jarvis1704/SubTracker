package com.biprangshu.subtracker.domain.usecase

import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import javax.inject.Inject

class AddSubscriptionUseCase @Inject constructor(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription): Long {
        // You could add validation logic here (e.g., "Price must be > 0")
        if (subscription.price >= 0) {
            return repository.insertSubscription(subscription)
        }
        return -1L
    }
}