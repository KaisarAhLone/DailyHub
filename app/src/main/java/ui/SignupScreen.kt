package com.example.dailyhub.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dailyhub.components.*
import com.example.dailyhub.data.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(navController: NavController) {

    val context = LocalContext.current
    val prefs = UserPreferences(context)
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { imageUri = it.toString() }
    }

    AppBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            GradientHeader("Signup")

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = profession,
                onValueChange = { profession = it },
                label = { Text("Profession") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            Button(onClick = { launcher.launch("image/*") }) {
                Text("Select Profile Image")
            }

            Spacer(Modifier.height(20.dp))

            FullButton("Continue") {

                if (name.isNotEmpty() && profession.isNotEmpty()) {

                    scope.launch {
                        prefs.saveUser(name, profession)
                        prefs.saveImage(imageUri)
                    }

                    navController.navigate("home") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            }
        }
    }
}