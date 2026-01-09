package com.biprangshu.subtracker.domain.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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

    }

    val isFirstLaunchFlow = dataStore.data.map { preferences ->
        preferences[IS_FIRST_LAUNCH] ?: true
    }

    val isBiometricEnabledFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[BIOMETRIC_ENABLED] ?: false
    }

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