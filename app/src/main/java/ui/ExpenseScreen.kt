package com.example.dailyhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.dailyhub.components.*
import com.example.dailyhub.data.UserPreferences
import kotlinx.coroutines.launch

// ✅ Data class
data class Expense(val title: String, val amount: Int)

@Composable
fun ExpenseScreen(navController: NavController) {

    val context = LocalContext.current
    val prefs = UserPreferences(context)
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var list by remember { mutableStateOf(listOf<Expense>()) }

    val saved by prefs.expensesFlow.collectAsState(initial = "")

    LaunchedEffect(saved) {
        if (saved.isNotEmpty()) {
            list = saved.split(",").mapNotNull {
                val p = it.split(":")
                if (p.size == 2) Expense(p[0], p[1].toInt()) else null
            }
        }
    }

    val total = list.sumOf { it.amount }

    // 🌈 COLORFUL BACKGROUND
    AppBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 🔥 HEADER
            GradientHeader("Expenses 💸")

            Spacer(Modifier.height(16.dp))

            // 🧾 INPUT CARD
            AppCard {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                FullButton("Add Expense") {

                    if (title.isNotEmpty() && amount.isNotEmpty()) {

                        val new = list + Expense(title, amount.toInt())
                        list = new

                        val str = new.joinToString(",") {
                            "${it.title}:${it.amount}"
                        }

                        scope.launch {
                            prefs.saveExpenses(str)
                        }

                        title = ""
                        amount = ""
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // 💰 TOTAL
            Text(
                text = "Total: ₹$total",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(10.dp))

            // 📋 LIST
            LazyColumn {
                items(list) { e ->

                    AppCard {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text("${e.title} ₹${e.amount}")

                            Button(
                                onClick = {
                                    val new = list.filter { it != e }
                                    list = new

                                    val str = new.joinToString(",") {
                                        "${it.title}:${it.amount}"
                                    }

                                    scope.launch {
                                        prefs.saveExpenses(str)
                                    }
                                }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}