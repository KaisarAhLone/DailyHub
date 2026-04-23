package com.example.dailyhub.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dailyhub.R
import com.example.dailyhub.components.AppBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {

    val context = LocalContext.current

    AppBackground {

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("About Developer") },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.ArrowBack, null)
                        }
                    }
                )
            }
        ) { pad ->

            Column(
                modifier = Modifier
                    .padding(pad)
                    .padding(20.dp)
            ) {

                // 👤 PROFILE IMAGE (CIRCLE + BORDER)
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )

                Spacer(Modifier.height(16.dp))

                // 👤 NAME
                Text(
                    text = "Kaisar Ahmad Lone",
                    style = MaterialTheme.typography.headlineMedium
                )

                // 💼 ROLE
                Text(
                    text = "Android Developer",
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(16.dp))

                Text("Developer of DailyHub app")

                Spacer(Modifier.height(20.dp))

                Divider()

                Spacer(Modifier.height(16.dp))

                // 📧 EMAIL
                ClickableRow(Icons.Default.Email, "lonekaisar15@gmail.com") {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:lonekaisar15@gmail.com")
                    }
                    context.startActivity(intent)
                }

                // 📞 PHONE
                ClickableRow(Icons.Default.Phone, "9086455406") {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:9086455406")
                    }
                    context.startActivity(intent)
                }

                // 🔗 GITHUB
                ClickableRow(Icons.Default.Link, "GitHub Profile") {
                    val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/KaisarAhLone"))
                    context.startActivity(intent)
                }

                // 🔗 LINKEDIN
                ClickableRow(Icons.Default.Link, "LinkedIn Profile") {
                    val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.linkedin.com/in/kaisar-ah-lone-/"))
                    context.startActivity(intent)
                }

                Spacer(Modifier.height(20.dp))

                Divider()

                Spacer(Modifier.height(20.dp))

                // 🌐 PORTFOLIO
                Text(
                    text = "View Portfolio",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://dulcet-jalebi-27ff6f.netlify.app"))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

// 🔘 REUSABLE CLICKABLE ROW
@Composable
fun ClickableRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(12.dp))
        Text(text)
    }
}