package com.example.dailyhub.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dailyhub.data.UserPreferences
import com.example.dailyhub.components.*
import kotlinx.coroutines.launch

// ✅ Data class
data class Loan(val person: String, val amount: Int)

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanScreen(navController: NavController) {

    val context = LocalContext.current
    val prefs = UserPreferences(context)
    val scope = rememberCoroutineScope()

    var person by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var list by remember { mutableStateOf(listOf<Loan>()) }

    val saved by prefs.loansFlow.collectAsState(initial = "")

    // 🔄 Load data
    LaunchedEffect(saved) {
        if (saved.isNotEmpty()) {
            list = saved.split(",").mapNotNull {
                val p = it.split(":")
                if (p.size == 2 && p[1].toIntOrNull() != null) {
                    Loan(p[0], p[1].toInt())
                } else null
            }
        }
    }

    AppBackground {

        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent
        ) { pad ->

            Column(
                modifier = Modifier
                    .padding(pad)
                    .padding(16.dp)
            ) {

                GradientHeader("Loans 💰")

                Spacer(Modifier.height(16.dp))

                AppCard {

                    OutlinedTextField(
                        value = person,
                        onValueChange = { person = it },
                        label = { Text("Person Name") },
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

                    FullButton("Add Loan") {

                        val amt = amount.toIntOrNull()

                        if (person.isNotEmpty() && amt != null) {

                            val new = list + Loan(person, amt)
                            list = new

                            val str = new.joinToString(",") {
                                "${it.person}:${it.amount}"
                            }

                            scope.launch {
                                prefs.saveLoans(str)
                            }

                            person = ""
                            amount = ""
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                LazyColumn {
                    items(list) { loan ->
                        AppCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text("${loan.person} ₹${loan.amount}")

                                Button(onClick = {
                                    val new = list.filter { it != loan }
                                    list = new

                                    val str = new.joinToString(",") {
                                        "${it.person}:${it.amount}"
                                    }

                                    scope.launch {
                                        prefs.saveLoans(str)
                                    }
                                }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}