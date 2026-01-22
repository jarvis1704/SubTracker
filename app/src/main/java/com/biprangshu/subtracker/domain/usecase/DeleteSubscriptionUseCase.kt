package com.biprangshu.subtracker.domain.usecase

import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.worker.ReminderScheduler
import javax.inject.Inject

class DeleteSubscriptionUseCase @Inject constructor(
    private val repository: SubscriptionRepository,
    private val reminderScheduler: ReminderScheduler
) {
    suspend operator fun invoke(subscription: Subscription) {
        reminderScheduler.cancelReminder(subscription.id)

        repository.deleteSubscription(subscription)
    }
}