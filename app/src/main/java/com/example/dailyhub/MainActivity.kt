package com.example.dailyhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.dailyhub.data.UserPreferences
import com.example.dailyhub.ui.*
import kotlinx.coroutines.flow.first

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val context = LocalContext.current
            val userPreferences = UserPreferences(context)

            var isReady by remember { mutableStateOf(false) }
            var startDestination by remember { mutableStateOf("signup") }

            // 🔥 Check login status once
            LaunchedEffect(Unit) {
                val loggedIn = userPreferences.isLoggedIn.first()
                startDestination = if (loggedIn) "home" else "signup"
                isReady = true
            }

            if (isReady) {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {

                    // Signup
                    composable("signup") {
                        SignupScreen(navController)
                    }

                    // Home
                    composable("home") {
                        HomeScreen(navController)
                    }

                    // Expense
                    composable("expense") {
                        ExpenseScreen(navController)
                    }

                    // Loan
                    composable("loan") {
                        LoanScreen(navController)
                    }

                    // Marketplace
                    composable("market") {
                        MarketplaceScreen(navController)
                    }

                    // Settings
                    composable("settings") {
                        SettingsScreen(navController)
                    }

                    // Profile
                    composable("profile") {
                        ProfileScreen(navController)
                    }

                    // About 🔥
                    composable("about") {
                        AboutScreen(navController)
                    }
                }

            } else {
                // Loading screen
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}