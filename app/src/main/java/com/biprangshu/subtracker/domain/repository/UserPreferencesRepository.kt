package com.biprangshu.subtracker.domain.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val dataStore = context.dataStore

    companion object {

        private val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        private val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        //theme choice can be added later

        // AI Preferences
        private val AI_OPTIMIZER_ENABLED = booleanPreferencesKey("ai_optimizer_enabled")
        private val AI_BURN_RATE_ENABLED = booleanPreferencesKey("ai_burn_rate_enabled")
        private val AI_PRICE_ALERTS_ENABLED = booleanPreferencesKey("ai_price_alerts_enabled")
        private val AI_PERIODICITY_DAYS = intPreferencesKey("ai_periodicity_days")
    }


    val isFirstLaunchFlow = dataStore.data.map { preferences ->
        preferences[IS_FIRST_LAUNCH] ?: true
    }

    val isBiometricEnabledFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[BIOMETRIC_ENABLED] ?: false
    }

    //ai flows
    val aiOptimizerEnabledFlow = dataStore.data.map { it[AI_OPTIMIZER_ENABLED] ?: true }
    val aiBurnRateEnabledFlow = dataStore.data.map { it[AI_BURN_RATE_ENABLED] ?: true }
    val aiPriceAlertsEnabledFlow = dataStore.data.map { it[AI_PRICE_ALERTS_ENABLED] ?: true }
    val aiPeriodicityFlow = dataStore.data.map { it[AI_PERIODICITY_DAYS] ?: 7 } //default 7 days

    suspend fun setFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    suspend fun setBiometricEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED] = isEnabled
        }
    }

    //ai setters
    suspend fun setAiOptimizerEnabled(enabled: Boolean) {
        dataStore.edit { it[AI_OPTIMIZER_ENABLED] = enabled }
    }

    suspend fun setAiBurnRateEnabled(enabled: Boolean) {
        dataStore.edit { it[AI_BURN_RATE_ENABLED] = enabled }
    }

    suspend fun setAiPriceAlertsEnabled(enabled: Boolean) {
        dataStore.edit { it[AI_PRICE_ALERTS_ENABLED] = enabled }
    }

    suspend fun setAiPeriodicity(days: Int) {
        dataStore.edit { it[AI_PERIODICITY_DAYS] = days.coerceIn(3, 7) }
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(IS_FIRST_LAUNCH)
            preferences.remove(BIOMETRIC_ENABLED)

        }
    }

    suspend fun nukeAllPreferences(){
        //as it says, use with caution
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}