package com.biprangshu.subtracker.ui.screens.HomeScreen

import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.ui.components.sharedBoundsReveal
import com.biprangshu.subtracker.ui.screens.HomeScreen.components.EmptyHomeState
import com.biprangshu.subtracker.ui.screens.HomeScreen.components.SubscriptionCard
import com.biprangshu.subtracker.ui.screens.HomeScreen.viewmodel.HomeViewModel
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onNavigate: (Route) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope
) {

    val subscriptions by viewModel.subscriptions.collectAsState()
    val totalMonthly by viewModel.totalMonthlySpend.collectAsState()

    val userData by viewModel.userData.collectAsState()

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


            if (subscriptions.isNotEmpty()) {
                //dynamic Spending Details
                Text(
                    text = "Total Monthly: ${userData?.preferredCurrency}${String.format("%.2f", totalMonthly)}",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))

                // Note: Yearly logic can be added to ViewModel later
                Text(
                    text = "Total Yearly: ${userData?.preferredCurrency}${String.format("%.2f", totalMonthly * 12)}",
                    style = MaterialTheme.typography.titleLarge
                )
            } else {

                Text(
                    text = "Total Monthly: ${userData?.preferredCurrency}0.00",
                    style = MaterialTheme.typography.displaySmall.copy(color = colorScheme.outline),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))

            // list of subscriptions or Empty State
            if (subscriptions.isEmpty()) {
                EmptyHomeState(
                    modifier = Modifier.weight(1f),
                    onAddClick = { onNavigate(Route.AddSubscriptionScreen) }
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(

                        bottom = innerPadding.calculateBottomPadding() + 16.dp
                    )
                ) {
                    items(subscriptions) { subscription ->
                        val transitionKey = "subscription-${subscription.id}"

                        SubscriptionCard(
                            subscription = subscription,
                            onNavigate = {
                                    route ->
                                onNavigate(route)
                            },
                            modifier = Modifier.sharedBoundsReveal(
                                sharedTransitionScope = sharedTransitionScope,
                                sharedContentState = sharedTransitionScope.rememberSharedContentState(key = transitionKey)
                            ),
                            preferedCurrency = userData?.preferredCurrency ?: "$"
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

