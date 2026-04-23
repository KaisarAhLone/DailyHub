package com.example.dailyhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.dailyhub.data.UserPreferences
import kotlinx.coroutines.launch

data class Expense(val title: String, val amount: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(navController: NavController) {

    val context = LocalContext.current
    val prefs = UserPreferences(context)
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var list by remember { mutableStateOf(listOf<Expense>()) }

    val saved by prefs.expensesFlow.collectAsState("")

    LaunchedEffect(saved) {
        if (saved.isNotEmpty()) {
            list = saved.split(",").mapNotNull {
                val p = it.split(":")
                if (p.size == 2) Expense(p[0], p[1].toInt()) else null
            }
        }
    }

    val total = list.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") },
                navigationIcon = {
                    IconButton({ navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { pad ->

        Column(Modifier.padding(pad).padding(16.dp)) {

            OutlinedTextField(title, { title = it }, label = { Text("Title") })
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(amount, { amount = it }, label = { Text("Amount") })

            Spacer(Modifier.height(8.dp))

            Button({
                if (title.isNotEmpty() && amount.isNotEmpty()) {
                    val new = list + Expense(title, amount.toInt())
                    list = new

                    val str = new.joinToString(",") { "${it.title}:${it.amount}" }
                    scope.launch { prefs.saveExpenses(str) }

                    title = ""; amount = ""
                }
            }) { Text("Add") }

            Spacer(Modifier.height(10.dp))
            Text("Total: ₹$total")

            LazyColumn {
                items(list) { e ->
                    Row(
                        Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${e.title} ₹${e.amount}")

                        Button({
                            val new = list.filter { it != e }
                            list = new
                            val str = new.joinToString(",") { "${it.title}:${it.amount}" }
                            scope.launch { prefs.saveExpenses(str) }
                        }) { Text("Delete") }
                    }
                }
            }
        }
    }
}