package com.biprangshu.subtracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.lazy.LazyColumn
import com.biprangshu.subtracker.R
import com.biprangshu.subtracker.ui.components.Subscription
import com.biprangshu.subtracker.ui.components.SubscriptionCard
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {

    val subscription = listOf(
        Subscription(
            name = "Netflix",
            price = 9.99,
            dueInDays = 5,
            logoResId = R.drawable.netflix_logo
        ),
        Subscription(
            name = "Spotify",
            price = 4.99,
            dueInDays = 12,
            logoResId = R.drawable.spotify_logo
        ),
        Subscription(
            name = "Disney+",
            price = 14.99,
            dueInDays = 20,
            logoResId = R.drawable.disney
        ),
        Subscription(
            name = "YouTube Premium",
            price = 11.99,
            dueInDays = 25,
            logoResId = R.drawable.youtube
        )
    )


    Surface(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        Column(
            modifier= Modifier.fillMaxSize().padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Text(
                "SubTracker",
                style = TextStyle(
                    fontFamily = robotoFlexTopBar,
                    fontSize = 32.sp,
                    lineHeight = 34.sp,
                    color = colorScheme.primary
                ),
            )
            Spacer(Modifier.height(32.dp))
            //dyanamic spending details

            Text(
                "Total Monthly: $29.99",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Total Yearly: $299.99",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(24.dp))
            //list of subscriptions
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(subscription) { subscription ->
                    SubscriptionCard(subscription = subscription)
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}