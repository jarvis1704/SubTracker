package com.biprangshu.subtracker.domain.usecase

import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import javax.inject.Inject

class AddSubscriptionUseCase @Inject constructor(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription): Long {
        if (subscription.price < 0) {
            throw IllegalArgumentException("Price cannot be negative")
        }
        return repository.insertSubscription(subscription)
    }
}