package com.example.dailyhub.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dailyhub.components.AppBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    AppBackground {

        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            topBar = {
                TopAppBar(title = { Text("Settings") })
            }
        ) { pad ->

            Column(
                modifier = Modifier
                    .padding(pad)
                    .padding(16.dp)
            ) {

                SettingItem("Notifications", Icons.Default.Notifications)
                SettingItem("Help", Icons.Default.Help)
                SettingItem("Reminder", Icons.Default.Alarm)

                SettingItem("About", Icons.Default.Info) {
                    navController.navigate("about")
                }

                SettingItem("Logout", Icons.Default.ExitToApp)
            }
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null)
        Spacer(Modifier.width(16.dp))
        Text(title)
    }
}