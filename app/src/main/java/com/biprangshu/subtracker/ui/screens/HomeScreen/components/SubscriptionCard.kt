package com.biprangshu.subtracker.ui.screens.HomeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.biprangshu.subtracker.navigation.Route


//dummy data class, will be changed with room implemention
//todo: replace with room source
data class Subscription(
    val name: String,
    val price: Double,
    val dueInDays: Int,
    val logoResId: Int
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SubscriptionCard(
    modifier: Modifier = Modifier,
    subscription: Subscription,
    onNavigate: (Route) -> Unit
) {

    Card(
        modifier = modifier.fillMaxWidth().clickable{
            onNavigate(Route.SubscriptionDetailsScreen)
        },
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = subscription.name,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text= "$${subscription.price}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Due in ${subscription.dueInDays} Days",
                    style = MaterialTheme.typography.labelLargeEmphasized
                )
            }

            //image
            AsyncImage(
                model = subscription.logoResId,
                contentDescription = "Netflix logo",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}