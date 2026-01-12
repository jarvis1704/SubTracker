package com.biprangshu.subtracker.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.biprangshu.subtracker.showCurrencySetModal
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.AnalyticsScreen
import com.biprangshu.subtracker.ui.screens.HomeScreen.HomeScreen
import com.biprangshu.subtracker.ui.screens.Settings.screens.AboutScreen
import com.biprangshu.subtracker.ui.screens.Settings.screens.SettingsScreen
import com.biprangshu.subtracker.ui.screens.addsubscriptionscreen.AddSubscriptionDetailsScreen
import com.biprangshu.subtracker.ui.screens.addsubscriptionscreen.AddSubscriptionScreen
import com.biprangshu.subtracker.ui.screens.onboarding.OnboardingScreen
import com.biprangshu.subtracker.ui.screens.onboarding.viewmodel.OnboardingViewModel
import com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.SubscriptionDetailsScreen

@Composable
fun NavGraph(
    backStack: MutableList<NavKey>,
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel,
    innerPadding: PaddingValues
) {

    //similar tp to navhost in navigation component
    NavDisplay(
        backStack = backStack,
        entryProvider = { key->
            when(key){
                is Route.HomeScreen -> {
                    NavEntry(key){
                        HomeScreen(
                            innerPadding = innerPadding,
                            onNavigate = {
                                route ->
                                backStack.add(route)
                            }
                        )
                    }
                }

                is Route.AnalyticsScreen -> {
                    NavEntry(key){
                        AnalyticsScreen(
                            innerPadding = innerPadding
                        )
                    }
                }

                is Route.SettingsScreen -> {
                    NavEntry(key){
                        SettingsScreen(
                            innerPadding = innerPadding,
                            onNavigate = {
                                route ->
                                backStack.add(route)
                            }
                        )
                    }
                }

                is Route.AddSubscriptionScreen -> {
                    NavEntry(key){
                        AddSubscriptionScreen(
                            innerPadding = innerPadding,
                            onNavigate = {
                                route ->
                                backStack.add(route)
                            }
                        )
                    }
                }

                is Route.SubscriptionDetailsScreen -> {
                    NavEntry(key){
                        SubscriptionDetailsScreen(
                            innerPadding = innerPadding,
                            subscriptionId = key.subscriptionId,
                            onBackClick = {
                                backStack.removeAt(backStack.lastIndex)
                            }
                        )
                    }
                }

                is Route.OnboardingScreen -> {
                    NavEntry(key){
                        OnboardingScreen(
                            onOnboardComplete = {
                                budget, currency, route ->
                                //clear backstack and navigate to home screen
                                onboardingViewModel.saveBudget(
                                    budget = budget,
                                    currency= currency,
                                    onSuccess = {
                                        Log.d("NavGraph","Onboarding complete and data written to room, navigating to $route")
                                        showCurrencySetModal = false
                                        backStack.clear()
                                        backStack.add(route)
                                    }
                                )

                            },
                            onGetStartedClick = {
                                showCurrencySetModal = true
                                onboardingViewModel.updateFirstAppOpen(false)
                            }
                        )
                    }
                }

                is Route.AddSubscriptionDetailsScreen -> {
                    NavEntry(key){
                        AddSubscriptionDetailsScreen(
                            innerPaddingValues = innerPadding,
                            name = key.name,
                            iconResId = key.iconRes,
                            onBackClick = {
                                backStack.removeAt(backStack.lastIndex)
                            },
                            onSaveSuccess = {
                                // Remove the "Add" screens from backstack to return Home
                                // Or just pop back once.
                                // Let's pop back to Home to show the new item.
                                while (backStack.last() !is Route.HomeScreen) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                            }
                        )
                    }
                }

                is Route.AboutScreen ->{
                    NavEntry(key){
                        AboutScreen(
                            onBack = {
                                backStack.removeAt(backStack.lastIndex)
                            }
                        )
                    }
                }

                else -> error("Unknown route: $key")
            }
        }
    )

}