package com.example.dailyhub.ui

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
import com.example.dailyhub.ui.components.*
import kotlinx.coroutines.launch

// ✅ Data class
data class Item(val name: String, val price: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(navController: NavController) {

    val context = LocalContext.current
    val prefs = UserPreferences(context)
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var list by remember { mutableStateOf(listOf<Item>()) }

    val saved by prefs.marketFlow.collectAsState(initial = "")

    // 🔄 Load saved data
    LaunchedEffect(saved) {
        if (saved.isNotEmpty()) {
            list = saved.split(",").mapNotNull {
                val p = it.split(":")
                if (p.size == 2) Item(p[0], p[1].toInt()) else null
            }
        }
    }

    Scaffold { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
        ) {

            // 🎨 Header
            GradientHeader("Marketplace 🛒")

            Spacer(modifier = Modifier.height(16.dp))

            // 📦 Input Card
            AppCard {

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                FullButton("Add Item") {
                    if (name.isNotEmpty() && price.isNotEmpty()) {

                        val new = list + Item(name, price.toInt())
                        list = new

                        val str = new.joinToString(",") {
                            "${it.name}:${it.price}"
                        }

                        scope.launch {
                            prefs.saveMarket(str)
                        }

                        name = ""
                        price = ""
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 📋 Items List
            LazyColumn {
                items(list) { item ->
                    AppCard {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text("${item.name} ₹${item.price}")

                            Button(
                                onClick = {
                                    val new = list.filter { it != item }
                                    list = new

                                    val str = new.joinToString(",") {
                                        "${it.name}:${it.price}"
                                    }

                                    scope.launch {
                                        prefs.saveMarket(str)
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