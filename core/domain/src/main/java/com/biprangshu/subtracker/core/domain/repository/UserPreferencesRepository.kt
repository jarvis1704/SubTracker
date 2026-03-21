package com.biprangshu.subtracker.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val isFirstLaunchFlow: Flow<Boolean>
    val isBiometricEnabledFlow: Flow<Boolean>
    val notificationsEnabledFlow: Flow<Boolean>
    val paymentRemindersEnabledFlow: Flow<Boolean>
    val aiOptimizerEnabledFlow: Flow<Boolean>
    val aiBurnRateEnabledFlow: Flow<Boolean>
    val aiPriceAlertsEnabledFlow: Flow<Boolean>
    val aiPeriodicityFlow: Flow<Int>

    suspend fun setFirstLaunch(isFirstLaunch: Boolean)
    suspend fun setBiometricEnabled(isEnabled: Boolean)
    suspend fun setAiOptimizerEnabled(enabled: Boolean)
    suspend fun setAiBurnRateEnabled(enabled: Boolean)
    suspend fun setAiPriceAlertsEnabled(enabled: Boolean)
    suspend fun setAiPeriodicity(days: Int)
    suspend fun setNotificationsEnabled(enabled: Boolean)
    suspend fun setPaymentRemindersEnabled(enabled: Boolean)
    suspend fun clearUserData()
    suspend fun nukeAllPreferences()
}
