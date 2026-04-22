package com.example.dailyhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to DailyHub",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { }) {
            Text(text = "Add Expense")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { }) {
            Text(text = "View Loans")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { }) {
            Text(text = "Marketplace")
        }
    }
}