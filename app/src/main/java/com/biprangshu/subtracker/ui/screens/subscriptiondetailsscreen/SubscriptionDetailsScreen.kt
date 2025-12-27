package com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biprangshu.subtracker.R
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionDetailsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onBackClick: () -> Unit = {}
) {
    // Shapes for grouped list items (Expressive Style)
    val topItemShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    val middleItemShape = RoundedCornerShape(4.dp)
    val bottomItemShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)

    // Scroll behavior for the Large Top Bar
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Netflix",
                        style = TextStyle(
                            fontFamily = robotoFlexTopBar,
                            fontSize = 32.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Edit */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = colorScheme.surface,
                    scrolledContainerColor = colorScheme.surfaceContainer
                )
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(scaffoldPadding)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 1. Hero Card (Subscription Info)
            SubscriptionHeroCard()

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Details Section
            Text(
                text = "Details",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                DetailItem(
                    icon = Icons.Default.Movie,
                    label = "Category",
                    value = "Entertainment",
                    shape = topItemShape
                )
                DetailItem(
                    icon = Icons.Default.DateRange,
                    label = "Billing Cycle",
                    value = "Monthly",
                    shape = middleItemShape
                )
                DetailItem(
                    icon = Icons.Default.CreditCard,
                    label = "Payment Method",
                    value = "Visa ending in 4567",
                    shape = middleItemShape
                )
                DetailItem(
                    icon = Icons.Default.Notifications,
                    label = "Reminder",
                    value = "3 days before",
                    shape = bottomItemShape
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Spending History Chart
            Text(
                text = "Spending History",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
            Text(
                text = "Last 6 months of $15.99 payments",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp, start = 4.dp)
            )

            SpendingHistoryChart()

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Cancel Button
            Button(
                onClick = { /* TODO: Cancel Logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.error,
                    contentColor = colorScheme.onError
                ),
                shape = RoundedCornerShape(28.dp) // Expressive pill shape
            ) {
                Text(
                    "Cancel Subscription",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Bottom padding to clear floating bars if any
            Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding() + 24.dp))
        }
    }
}

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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo (Placeholder using Icon, ideally this is an Image)
            Icon(
                painter = painterResource(id = R.drawable.netflix_logo), // Ensure this resource exists or use a generic icon
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color.Unspecified
            )

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

@Composable
fun DetailItem(
    icon: ImageVector,
    label: String,
    value: String,
    shape: androidx.compose.ui.graphics.Shape
) {
    ListItem(
        headlineContent = {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onSurfaceVariant
            )
        },
        supportingContent = {
            Text(
                value,
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.onSurface
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorScheme.primary
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = colorScheme.surfaceContainerHigh
        ),
        modifier = Modifier.clip(shape)
    )
}

@Composable
fun SpendingHistoryChart() {
    // Simple mock data for 6 months
    val history = listOf(15.99, 15.99, 15.99, 15.99, 15.99, 15.99)
    val labels = listOf("$15.99", "$15.99", "$15.99", "$15.99", "$15.99", "$15.99")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        history.forEachIndexed { index, amount ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.height(80.dp)
            ) {
                // Price Label on top of bar
                Text(
                    text = labels[index],
                    style = MaterialTheme.typography.labelSmall,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                // The Bar
                Box(
                    modifier = Modifier
                        .width(42.dp) // Expressive wide bars
                        .height(32.dp) // Fixed height for visual consistency
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(colorScheme.primaryContainer)
                )
            }
        }
    }
}