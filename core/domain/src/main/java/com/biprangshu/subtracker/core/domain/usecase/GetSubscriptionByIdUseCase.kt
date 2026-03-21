package com.biprangshu.subtracker.core.domain.usecase

import com.biprangshu.subtracker.core.domain.model.Subscription
import com.biprangshu.subtracker.core.domain.repository.SubscriptionRepository
import javax.inject.Inject

class GetSubscriptionByIdUseCase @Inject constructor(
    private val repository: SubscriptionRepository
) {

    suspend operator fun invoke(id: Int): Subscription? {
        return repository.getSubscriptionById(id)
    }

}
