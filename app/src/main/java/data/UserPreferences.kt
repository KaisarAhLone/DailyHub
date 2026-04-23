package com.example.dailyhub.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "dailyhub_prefs")

class UserPreferences(private val context: Context) {

    // =========================
    // 🔑 KEYS
    // =========================
    private val nameKey = stringPreferencesKey("user_name")
    private val professionKey = stringPreferencesKey("user_profession")
    private val imageKey = stringPreferencesKey("user_image")

    private val expensesKey = stringPreferencesKey("expenses")
    private val loansKey = stringPreferencesKey("loans")
    private val marketKey = stringPreferencesKey("market")

    // =========================
    // 👤 USER
    // =========================
    val userFlow: Flow<Pair<String, String>> =
        context.dataStore.data.map {
            Pair(
                it[nameKey] ?: "",
                it[professionKey] ?: ""
            )
        }

    val isLoggedInFlow: Flow<Boolean> =
        context.dataStore.data.map {
            !it[nameKey].isNullOrEmpty()
        }

    val imageFlow: Flow<String> =
        context.dataStore.data.map {
            it[imageKey] ?: ""
        }

    suspend fun saveUser(name: String, profession: String) {
        context.dataStore.edit {
            it[nameKey] = name
            it[professionKey] = profession
        }
    }

    suspend fun saveImage(uri: String) {
        context.dataStore.edit {
            it[imageKey] = uri
        }
    }

    // =========================
    // 💸 EXPENSES
    // =========================
    val expensesFlow: Flow<String> =
        context.dataStore.data.map {
            it[expensesKey] ?: ""
        }

    suspend fun saveExpenses(data: String) {
        context.dataStore.edit {
            it[expensesKey] = data
        }
    }

    // =========================
    // 💰 LOANS
    // =========================
    val loansFlow: Flow<String> =
        context.dataStore.data.map {
            it[loansKey] ?: ""
        }

    suspend fun saveLoans(data: String) {
        context.dataStore.edit {
            it[loansKey] = data
        }
    }

    // =========================
    // 🛒 MARKET
    // =========================
    val marketFlow: Flow<String> =
        context.dataStore.data.map {
            it[marketKey] ?: ""
        }

    suspend fun saveMarket(data: String) {
        context.dataStore.edit {
            it[marketKey] = data
        }
    }

    // =========================
    // 🚪 LOGOUT
    // =========================
    suspend fun clearUser() {
        context.dataStore.edit {
            it.clear()
        }
    }
}
