package com.example.dailyhub.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dailyhub.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {

    val context = LocalContext.current

    fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Developer") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔥 Profile Image
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 👤 Name
            Text(
                text = "Kaisar A. Lone",
                style = MaterialTheme.typography.headlineSmall
            )

            // 💼 Role
            Text(
                text = "Android Developer",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 📄 Description
            Text(
                text = "Developer of DailyHub — an Android application focused on managing daily activities like expenses, loans, and local marketplace interactions.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(30.dp))

            Divider()

            Spacer(modifier = Modifier.height(20.dp))

            // 📧 Email
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openLink("mailto:lonekaisar15@gmail.com") }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Email, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text("lonekaisar15@gmail.com")
            }

            // 📞 Phone
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openLink("tel:9086455406") }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Phone, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text("9086455406")
            }

            // 🔗 GitHub
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        openLink("https://github.com/KaisarAhLone")
                    }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Link, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text("GitHub Profile")
            }

            // 🔗 LinkedIn
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        openLink("https://www.linkedin.com/in/kaisar-ah-lone-/")
                    }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Link, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text("LinkedIn Profile")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Divider()

            Spacer(modifier = Modifier.height(10.dp))

            // 🌐 Portfolio
            Text(
                text = "View Portfolio",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    openLink("https://dulcet-jalebi-27ff6f.netlify.app")
                }
            )
        }
    }
}