package com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.biprangshu.subtracker.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SubscriptionHeroCard() {
    // Using a specific red to match Netflix brand from the image,
    // but mixing it with the theme could also work.
    val brandColor = Color(0xFFE50914)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(32.dp), // Extra large styling
        colors = CardDefaults.cardColors(
            containerColor = brandColor,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo (Placeholder using Icon, ideally this is an Image)
            Surface(
                modifier = Modifier.size(52.dp),
                shape = MaterialShapes.Cookie12Sided.toShape(),
                color = Color.White // White background for logos usually looks best
            ) {
                AsyncImage(
                    model = R.drawable.netflix_logo, // Ensure this resource exists or use a generic icon
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).padding(8.dp),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Standard Plan",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            )

            Text(
                text = "$15.99",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "/mo",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.White.copy(alpha = 0.8f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Status Pills
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Active",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = " • Next payment on Dec 25, 2025",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )
            }
        }
    }
}