package com.example.dailyhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.dailyhub.data.UserPreferences
import com.example.dailyhub.ui.*
import com.example.dailyhub.ui.theme.DailyHubTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DailyHubTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs = UserPreferences(context)

    val isLoggedIn by prefs.isLoggedInFlow.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "home" else "signup"
    ) {

        composable("signup") { SignupScreen(navController) }

        composable("home") { HomeScreen(navController) }

        composable("expense") { ExpenseScreen(navController) }

        composable("loan") { LoanScreen(navController) }

        composable("market") { MarketplaceScreen(navController) }

        composable("profile") { ProfileScreen(navController) }

        composable("settings") { SettingsScreen(navController) }

        // 🔥 IMPORTANT (fix your crash)
        composable("about") { AboutScreen(navController) }
    }
}