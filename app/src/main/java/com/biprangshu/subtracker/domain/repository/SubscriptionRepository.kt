package com.biprangshu.subtracker.domain.repository

import com.biprangshu.subtracker.domain.model.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getAllSubscriptions(): Flow<List<Subscription>>
    suspend fun getSubscriptionById(id: Int): Subscription?
    suspend fun insertSubscription(subscription: Subscription)
    suspend fun deleteSubscription(subscription: Subscription)
    fun getTotalMonthlySpend(): Flow<Double>
}