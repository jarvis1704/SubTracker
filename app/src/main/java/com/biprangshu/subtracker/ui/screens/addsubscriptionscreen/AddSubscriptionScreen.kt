package com.biprangshu.subtracker.ui.screens.addsubscriptionscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.biprangshu.subtracker.R
import com.biprangshu.subtracker.ui.screens.addsubscriptionscreen.components.SubscriptionOptionCard
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

// Simple data class for the options in this screen
data class SubscriptionOption(
    val name: String,
    val iconRes: Int
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddSubscriptionScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    // Data taken from HomeScreen list
    val popularServices = remember {
        listOf(
            SubscriptionOption("Netflix", R.drawable.netflix_logo),
            SubscriptionOption("Spotify", R.drawable.spotify_logo),
            SubscriptionOption("Disney+", R.drawable.disney),
            SubscriptionOption("YouTube Premium", R.drawable.youtube),
            // Added a few placeholders for a fuller list feel
//            SubscriptionOption("Amazon Prime", R.drawable.amazon_prime), // Assuming you might have this or similar
//            SubscriptionOption("Apple Music", R.drawable.apple_music)    // Assuming you might have this
        )
    }

    var searchQuery by remember { mutableStateOf("") }

    val filteredServices = if (searchQuery.isEmpty()) {
        popularServices
    } else {
        popularServices.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() + 24.dp),
        ) {
            // Header Section
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Add Subscription",
                    style = MaterialTheme.typography.headlineLargeEmphasized
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Search or choose from popular services",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(24.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search services...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    shape = RoundedCornerShape(24.dp), // Expressive roundness
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.3f),
                        unfocusedContainerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.3f),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Popular Services",
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )

            // List of Subscriptions
            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = innerPadding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredServices) { service ->
                    SubscriptionOptionCard(service = service)
                }
            }
        }
    }
}

