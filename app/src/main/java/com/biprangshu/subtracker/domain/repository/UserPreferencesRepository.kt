package com.biprangshu.subtracker.domain.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
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
        //theme choice can be added later

    }

    val isFirstLaunchFlow = dataStore.data.map { preferences ->
        preferences[IS_FIRST_LAUNCH] ?: true
    }

    suspend fun setFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(IS_FIRST_LAUNCH)

        }
    }

    suspend fun nukeAllPreferences(){
        //as it says, use with caution
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}