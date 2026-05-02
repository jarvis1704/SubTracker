package com.biprangshu.subtracker.core.domain.usecase

import com.biprangshu.subtracker.core.domain.model.Subscription
import com.biprangshu.subtracker.core.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedSubscriptionsUseCase @Inject constructor(
    private val repository: SubscriptionRepository
) {
    operator fun invoke(): Flow<List<Subscription>> {
        return repository.getAllSubscriptions().map { list ->
            list.sortedBy { it.nextPaymentDate }
        }
    }
}
