package com.biprangshu.subtracker.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
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
import com.biprangshu.subtracker.ui.screens.editsubscriptionscreen.EditSubscriptionScreen
import com.biprangshu.subtracker.ui.screens.onboarding.OnboardingScreen
import com.biprangshu.subtracker.ui.screens.onboarding.viewmodel.OnboardingViewModel
import com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.SubscriptionDetailsScreen

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun NavGraph(
    backStack: MutableList<NavKey>,
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel,
    innerPadding: PaddingValues
) {
    val motionScheme = MaterialTheme.motionScheme

    SharedTransitionLayout(modifier = modifier) {
        // Access the SharedTransitionScope as 'this' here

        val motionScheme = MaterialTheme.motionScheme

        SharedTransitionLayout(
            modifier = modifier
        ) {
            NavDisplay(
                backStack = backStack,
                transitionSpec = {
                    fadeIn(motionScheme.defaultEffectsSpec())
                        .togetherWith(fadeOut(motionScheme.defaultEffectsSpec()))
                },
                // 2. Back Animation (Crucial for back nav to work)
                popTransitionSpec = {
                    fadeIn(motionScheme.defaultEffectsSpec())
                        .togetherWith(fadeOut(motionScheme.defaultEffectsSpec()))
                },
                entryProvider = { key ->
                    when (key) {
                        is Route.HomeScreen -> {
                            NavEntry(key) {
                                // Pass SharedTransitionScope to HomeScreen
                                HomeScreen(
                                    innerPadding = innerPadding,
                                    onNavigate = { route -> backStack.add(route) },
                                    sharedTransitionScope = this@SharedTransitionLayout
                                )
                            }
                        }

                        is Route.SubscriptionDetailsScreen -> {
                            NavEntry(key) {
                                // Pass SharedTransitionScope to DetailsScreen
                                SubscriptionDetailsScreen(
                                    innerPadding = innerPadding,
                                    subscriptionId = key.subscriptionId,
                                    onBackClick = { backStack.removeAt(backStack.lastIndex) },
                                    onEditClick = { id -> backStack.add(Route.EditSubscriptionScreen(id)) },
                                    sharedTransitionScope = this@SharedTransitionLayout
                                )
                            }
                        }


                        is Route.AnalyticsScreen -> {
                            NavEntry(key) { AnalyticsScreen(innerPadding = innerPadding) }
                        }
                        is Route.SettingsScreen -> {
                            NavEntry(key) {
                                SettingsScreen(
                                    innerPadding = innerPadding,
                                    onNavigate = {
                                            route -> backStack.add(route)
                                    }

                                )
                            }
                        }
                        is Route.AddSubscriptionScreen -> {
                            NavEntry(key) {
                                AddSubscriptionScreen(
                                    innerPadding = innerPadding,
                                    onNavigate = { route -> backStack.add(route) }
                                )
                            }
                        }
                        is Route.EditSubscriptionScreen -> {
                            NavEntry(key) {
                                EditSubscriptionScreen(
                                    subscriptionId = key.subscriptionId,
                                    innerPaddingValues = innerPadding,
                                    onBackClick = { backStack.removeAt(backStack.lastIndex) },
                                    onSaveSuccess = { backStack.removeAt(backStack.lastIndex) }
                                )
                            }
                        }
                        is Route.OnboardingScreen -> {
                            NavEntry(key) {
                                OnboardingScreen(
                                    onOnboardComplete = { budget, currency, route ->
                                        onboardingViewModel.saveBudget(budget, currency) {
                                            showCurrencySetModal = false
                                            backStack.clear()
                                            backStack.add(route)
                                        }
                                    },
                                    onGetStartedClick = {
                                        showCurrencySetModal = true
                                        onboardingViewModel.updateFirstAppOpen(false)
                                    }
                                )
                            }
                        }
                        is Route.AddSubscriptionDetailsScreen -> {
                            NavEntry(key) {
                                AddSubscriptionDetailsScreen(
                                    innerPaddingValues = innerPadding,
                                    name = key.name,
                                    iconResId = key.iconRes,
                                    onBackClick = { backStack.removeAt(backStack.lastIndex) },
                                    onSaveSuccess = {
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


    }
}