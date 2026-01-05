package com.biprangshu.subtracker.domain.usecase

import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import javax.inject.Inject

class GetSubscriptionbyIdUsecase @Inject constructor(
    private val repository: SubscriptionRepository
) {

    suspend operator fun invoke (id: Int): Subscription? {
        return repository.getSubscriptionById(id)
    }

}