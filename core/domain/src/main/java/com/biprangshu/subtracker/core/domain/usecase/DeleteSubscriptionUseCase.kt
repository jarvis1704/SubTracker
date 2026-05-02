package com.biprangshu.subtracker.core.domain.usecase

import com.biprangshu.subtracker.core.domain.model.Subscription
import com.biprangshu.subtracker.core.domain.repository.ReminderSchedulerContract
import com.biprangshu.subtracker.core.domain.repository.SubscriptionRepository
import javax.inject.Inject

class DeleteSubscriptionUseCase @Inject constructor(
    private val repository: SubscriptionRepository,
    private val reminderScheduler: ReminderSchedulerContract
) {
    suspend operator fun invoke(subscription: Subscription) {
        reminderScheduler.cancelReminder(subscription.id)

        repository.deleteSubscription(subscription)
    }
}
