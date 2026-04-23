package com.example.dailyhub.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {

        // 🔐 Login
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

        // 👤 User info (optional, future use)
        val NAME = stringPreferencesKey("name")
        val PROFESSION = stringPreferencesKey("profession")

        // 📊 App data
        val EXPENSES = stringPreferencesKey("expenses")
        val LOANS = stringPreferencesKey("loans")
        val MARKET = stringPreferencesKey("market")
    }

    // ✅ Save login + basic user data
    suspend fun saveUser(name: String, profession: String, imageUri: String?) {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = true
            prefs[NAME] = name
            prefs[PROFESSION] = profession
        }
    }

    // ✅ Check login status
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_LOGGED_IN] ?: false
    }

    // ✅ Logout (clear everything)
    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    // ==========================
    // 📊 EXPENSES
    // ==========================
    suspend fun saveExpenses(data: String) {
        context.dataStore.edit { prefs ->
            prefs[EXPENSES] = data
        }
    }

    val expensesFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[EXPENSES] ?: ""
    }

    // ==========================
    // 💰 LOANS
    // ==========================
    suspend fun saveLoans(data: String) {
        context.dataStore.edit { prefs ->
            prefs[LOANS] = data
        }
    }

    val loansFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[LOANS] ?: ""
    }

    // ==========================
    // 🛒 MARKETPLACE
    // ==========================
    suspend fun saveMarket(data: String) {
        context.dataStore.edit { prefs ->
            prefs[MARKET] = data
        }
    }

    val marketFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[MARKET] ?: ""
    }
}