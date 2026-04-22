package com.example.dailyhub.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val NAME = stringPreferencesKey("name")
        val PROFESSION = stringPreferencesKey("profession")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    // Save user data
    suspend fun saveUser(name: String, profession: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME] = name
            prefs[PROFESSION] = profession
            prefs[IS_LOGGED_IN] = true
        }
    }

    // Check if user already signed up
    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { prefs ->
            prefs[IS_LOGGED_IN] ?: false
        }
}