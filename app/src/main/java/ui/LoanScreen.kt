package com.example.dailyhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.dailyhub.data.UserPreferences
import kotlinx.coroutines.launch

data class Loan(val person: String, val amount: Int)

@Composable
fun LoanScreen(navController: NavController) {

    val context = LocalContext.current
    val prefs = UserPreferences(context)
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var list by remember { mutableStateOf(listOf<Loan>()) }

    val saved by prefs.loansFlow.collectAsState("")

    LaunchedEffect(saved) {
        if (saved.isNotEmpty()) {
            list = saved.split(",").mapNotNull {
                val p = it.split(":")
                if (p.size == 2) Loan(p[0], p[1].toInt()) else null
            }
        }
    }

    Scaffold { pad ->
        Column(Modifier.padding(pad).padding(16.dp)) {

            OutlinedTextField(name, { name = it }, label = { Text("Person") })
            OutlinedTextField(amount, { amount = it }, label = { Text("Amount") })

            Button({
                val new = list + Loan(name, amount.toInt())
                list = new
                val str = new.joinToString(",") { "${it.person}:${it.amount}" }
                scope.launch { prefs.saveLoans(str) }
                name = ""; amount = ""
            }) { Text("Add Loan") }

            LazyColumn {
                items(list) { l ->
                    Row(
                        Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${l.person} ₹${l.amount}")
                        Button({
                            val new = list.filter { it != l }
                            list = new
                            val str = new.joinToString(",") { "${it.person}:${it.amount}" }
                            scope.launch { prefs.saveLoans(str) }
                        }) { Text("Delete") }
                    }
                }
            }
        }
    }
}