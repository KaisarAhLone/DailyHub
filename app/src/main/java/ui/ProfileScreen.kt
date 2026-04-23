package com.example.dailyhub.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.dailyhub.components.AppBackground
import com.example.dailyhub.data.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    val context = LocalContext.current
    val prefs = UserPreferences(context)

    val user by prefs.userFlow.collectAsState(initial = Pair("", ""))
    val image by prefs.imageFlow.collectAsState(initial = "")

    AppBackground {

        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            topBar = {
                TopAppBar(title = { Text("Profile") })
            }
        ) { pad ->

            Column(
                modifier = Modifier
                    .padding(pad)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (image.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = if (user.first.isEmpty()) "No Name" else user.first,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = if (user.second.isEmpty()) "No Profession" else user.second
                )
            }
        }
    }
}