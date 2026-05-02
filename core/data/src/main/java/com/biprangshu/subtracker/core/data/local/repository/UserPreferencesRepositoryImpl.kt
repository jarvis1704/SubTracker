package com.biprangshu.subtracker.core.data.local.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.biprangshu.subtracker.core.domain.repository.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserPreferencesRepository {

    private val dataStore = context.dataStore

    companion object {
        private val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        private val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        private val AI_OPTIMIZER_ENABLED = booleanPreferencesKey("ai_optimizer_enabled")
        private val AI_BURN_RATE_ENABLED = booleanPreferencesKey("ai_burn_rate_enabled")
        private val AI_PRICE_ALERTS_ENABLED = booleanPreferencesKey("ai_price_alerts_enabled")
        private val AI_PERIODICITY_DAYS = intPreferencesKey("ai_periodicity_days")
        private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_master_enabled")
        private val PAYMENT_REMINDERS_ENABLED = booleanPreferencesKey("payment_reminders_enabled")
    }

    override val isFirstLaunchFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_FIRST_LAUNCH] ?: true
    }

    override val isBiometricEnabledFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[BIOMETRIC_ENABLED] ?: false
    }

    override val notificationsEnabledFlow: Flow<Boolean> = dataStore.data.map {
        it[NOTIFICATIONS_ENABLED] ?: true
    }

    override val paymentRemindersEnabledFlow: Flow<Boolean> = dataStore.data.map {
        it[PAYMENT_REMINDERS_ENABLED] ?: true
    }

    override val aiOptimizerEnabledFlow: Flow<Boolean> = dataStore.data.map {
        it[AI_OPTIMIZER_ENABLED] ?: true
    }

    override val aiBurnRateEnabledFlow: Flow<Boolean> = dataStore.data.map {
        it[AI_BURN_RATE_ENABLED] ?: true
    }

    override val aiPriceAlertsEnabledFlow: Flow<Boolean> = dataStore.data.map {
        it[AI_PRICE_ALERTS_ENABLED] ?: true
    }

    override val aiPeriodicityFlow: Flow<Int> = dataStore.data.map {
        it[AI_PERIODICITY_DAYS] ?: 7
    }

    override suspend fun setFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    override suspend fun setBiometricEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED] = isEnabled
        }
    }

    override suspend fun setAiOptimizerEnabled(enabled: Boolean) {
        dataStore.edit { it[AI_OPTIMIZER_ENABLED] = enabled }
    }

    override suspend fun setAiBurnRateEnabled(enabled: Boolean) {
        dataStore.edit { it[AI_BURN_RATE_ENABLED] = enabled }
    }

    override suspend fun setAiPriceAlertsEnabled(enabled: Boolean) {
        dataStore.edit { it[AI_PRICE_ALERTS_ENABLED] = enabled }
    }

    override suspend fun setAiPeriodicity(days: Int) {
        dataStore.edit { it[AI_PERIODICITY_DAYS] = days.coerceIn(3, 7) }
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { it[NOTIFICATIONS_ENABLED] = enabled }
    }

    override suspend fun setPaymentRemindersEnabled(enabled: Boolean) {
        dataStore.edit { it[PAYMENT_REMINDERS_ENABLED] = enabled }
    }

    override suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(IS_FIRST_LAUNCH)
            preferences.remove(BIOMETRIC_ENABLED)
        }
    }

    override suspend fun nukeAllPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
