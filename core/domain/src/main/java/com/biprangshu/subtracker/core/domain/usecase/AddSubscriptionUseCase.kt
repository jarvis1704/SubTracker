package com.biprangshu.subtracker.core.domain.usecase

import com.biprangshu.subtracker.core.domain.model.Subscription
import com.biprangshu.subtracker.core.domain.repository.SubscriptionRepository
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
