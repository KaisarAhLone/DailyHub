package com.example.dailyhub.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dailyhub.components.AppBackground
import com.example.dailyhub.components.GradientHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    AppBackground {

        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,

            topBar = {
                TopAppBar(
                    title = { Text("DailyHub") },
                    actions = {

                        IconButton(onClick = {
                            navController.navigate("profile")
                        }) {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }

                        IconButton(onClick = {
                            navController.navigate("settings")
                        }) {
                            Icon(Icons.Default.Settings, contentDescription = null)
                        }
                    }
                )
            }

        ) { pad ->

            Column(
                modifier = Modifier
                    .padding(pad)
                    .padding(16.dp)
            ) {

                GradientHeader("Welcome 👋")

                Spacer(Modifier.height(20.dp))

                // 💸 Expense
                DashboardCard("Expenses", Icons.Default.AttachMoney) {
                    navController.navigate("expense")
                }

                // 💰 Loan
                DashboardCard("Loans", Icons.Default.AccountBalance) {
                    navController.navigate("loan")
                }

                // 🛒 Market
                DashboardCard("Marketplace", Icons.Default.ShoppingCart) {
                    navController.navigate("market")
                }

                Spacer(Modifier.height(30.dp))

                // 🔥 FIXED ABOUT BUTTON
                Button(
                    onClick = {
                        navController.navigate("about")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("About Company")
                }
            }
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(icon, contentDescription = null)

            Spacer(Modifier.width(16.dp))

            Text(title, style = MaterialTheme.typography.titleMedium)
        }
    }
}