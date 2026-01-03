package com.biprangshu.subtracker.ui.screens.HomeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.ui.screens.HomeScreen.components.SubscriptionCard
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onNavigate: (Route) -> Unit
) {


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier= Modifier.fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
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
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    // 4. Add the bottom bar height (innerPadding.bottom) here
                    bottom = innerPadding.calculateBottomPadding() + 16.dp
                )
            ) {
//                items(subscription) { subscription ->
//                    SubscriptionCard(
//                        subscription = subscription,
//                        onNavigate = {
//                            route ->
//                            onNavigate(route)
//                        }
//                    )
//                    Spacer(Modifier.height(16.dp))
//                }
            }
        }
    }
}