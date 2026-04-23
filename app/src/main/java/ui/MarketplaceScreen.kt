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

data class Item(val name: String, val price: Int)

@Composable
fun MarketplaceScreen(navController: NavController) {

    val context = LocalContext.current
    val prefs = UserPreferences(context)
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var list by remember { mutableStateOf(listOf<Item>()) }

    val saved by prefs.marketFlow.collectAsState("")

    LaunchedEffect(saved) {
        if (saved.isNotEmpty()) {
            list = saved.split(",").mapNotNull {
                val p = it.split(":")
                if (p.size == 2) Item(p[0], p[1].toInt()) else null
            }
        }
    }

    Scaffold { pad ->
        Column(Modifier.padding(pad).padding(16.dp)) {

            OutlinedTextField(name, { name = it }, label = { Text("Item") })
            OutlinedTextField(price, { price = it }, label = { Text("Price") })

            Button({
                val new = list + Item(name, price.toInt())
                list = new
                val str = new.joinToString(",") { "${it.name}:${it.price}" }
                scope.launch { prefs.saveMarket(str) }
                name = ""; price = ""
            }) { Text("Add Item") }

            LazyColumn {
                items(list) { item ->
                    Row(
                        Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${item.name} ₹${item.price}")
                        Button({
                            val new = list.filter { it != item }
                            list = new
                            val str = new.joinToString(",") { "${it.name}:${it.price}" }
                            scope.launch { prefs.saveMarket(str) }
                        }) { Text("Delete") }
                    }
                }
            }
        }
    }
}