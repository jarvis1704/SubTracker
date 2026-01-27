package com.biprangshu.subtracker.domain.usecase

import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedSubscriptionsUseCase @Inject constructor(
    private val repository: SubscriptionRepository
) {
    operator fun invoke(): Flow<List<Subscription>> {
        return repository.getAllSubscriptions().map { list ->
            // Logic: Sort by closest due date first (using absolute timestamp)
            list.sortedBy { it.nextPaymentDate }
        }
    }
}
