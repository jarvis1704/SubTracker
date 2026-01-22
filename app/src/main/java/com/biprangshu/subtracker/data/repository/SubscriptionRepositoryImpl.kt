package com.biprangshu.subtracker.data.repository

import com.biprangshu.subtracker.data.local.SubscriptionDao
import com.biprangshu.subtracker.data.local.SubscriptionEntity
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
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



    private fun SubscriptionEntity.toDomain(): Subscription {
        val nextPaymentDate = calculateNextPaymentDate(firstPaymentDate, billingCycle)
        val daysUntil = calculateDaysUntil(nextPaymentDate)

        return Subscription(
            id = id,
            name = name,
            price = price,
            currency = currency,
            iconName = iconName,
            colorHex = colorHex,
            billingCycle = billingCycle,
            firstPaymentDate = firstPaymentDate,
            dueInDays = daysUntil,
            nextPaymentDate = nextPaymentDate,
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


    private fun calculateNextPaymentDate(startDate: Long, cycle: String): Long {
        val today = System.currentTimeMillis()


        if (startDate > today) {
            return startDate
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = startDate


        while (calendar.timeInMillis <= today) {
            when (cycle.lowercase()) {
                "yearly" -> calendar.add(Calendar.YEAR, 1)
                "weekly" -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
                else -> calendar.add(Calendar.MONTH, 1) // Default to Monthly
            }
        }
        return calendar.timeInMillis
    }


    private fun calculateDaysUntil(targetDate: Long): Int {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val target = Calendar.getInstance().apply {
            timeInMillis = targetDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val diff = target.timeInMillis - today.timeInMillis
        return TimeUnit.MILLISECONDS.toDays(diff).toInt().coerceAtLeast(0)
    }
}