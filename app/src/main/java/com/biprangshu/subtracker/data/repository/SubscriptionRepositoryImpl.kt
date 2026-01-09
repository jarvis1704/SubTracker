package com.biprangshu.subtracker.data.repository

import com.biprangshu.subtracker.data.local.SubscriptionDao
import com.biprangshu.subtracker.data.local.SubscriptionEntity
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(
    private val dao: SubscriptionDao
) : SubscriptionRepository {

    override fun getAllSubscriptions(): Flow<List<Subscription>> {
        return dao.getAllSubscriptions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSubscriptionById(id: Int): Subscription? {
        return dao.getSubscriptionById(id)?.toDomain()
    }

    override suspend fun insertSubscription(subscription: Subscription): Long {
        return dao.insertSubscription(subscription.toEntity())
    }

    override suspend fun deleteSubscription(subscription: Subscription) {
        dao.deleteSubscription(subscription.toEntity())
    }

    override fun getTotalMonthlySpend(): Flow<Double> {
        return dao.getTotalMonthlySpend().map { it ?: 0.0 }
    }

    // --- Mappers ---

    private fun SubscriptionEntity.toDomain(): Subscription {
        // Simple logic to calculate days remaining (can be expanded in a UseCase)
        val today = System.currentTimeMillis()
        // For now, let's assume firstPaymentDate is in the future or handle cycle math later
        val diff = firstPaymentDate - today
        val days = if (diff > 0) TimeUnit.MILLISECONDS.toDays(diff).toInt() else 0

        return Subscription(
            id = id,
            name = name,
            price = price,
            currency = currency,
            iconName = iconName,
            colorHex = colorHex,
            billingCycle = billingCycle,
            firstPaymentDate = firstPaymentDate,
            dueInDays = days,
            logoRedId = logoResId,
            category = category,
            paymentMethod = paymentMethod,
            remindersEnabled = remindersEnabled,
            reminderDaysBefore = reminderDaysBefore
        )
    }

    private fun Subscription.toEntity(): SubscriptionEntity {
        return SubscriptionEntity(
            id = id,
            name = name,
            price = price,
            currency = currency,
            billingCycle = billingCycle,
            firstPaymentDate = firstPaymentDate,
            iconName = iconName,
            colorHex = colorHex,
            logoResId = logoRedId,
            category = category,
            paymentMethod = paymentMethod,
            remindersEnabled = remindersEnabled,
            reminderDaysBefore = reminderDaysBefore
        )
    }
}