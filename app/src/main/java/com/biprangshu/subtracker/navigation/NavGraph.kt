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
    // Access the Expressive Motion Scheme from your Theme
    val motionScheme = MaterialTheme.motionScheme

    // 1. Wrap in SharedTransitionLayout to enable shared element transitions in the future
    // and provide a scope for the animations.
    SharedTransitionLayout(modifier = modifier) {

        NavDisplay(
            backStack = backStack,
            // 2. Define standard Entry/Exit animations using the motionScheme
            transitionSpec = {
                fadeIn(motionScheme.defaultEffectsSpec())
                    .togetherWith(fadeOut(motionScheme.defaultEffectsSpec()))
            },
            // 3. Define Pop (Back) animations
            popTransitionSpec = {
                fadeIn(motionScheme.defaultEffectsSpec())
                    .togetherWith(fadeOut(motionScheme.defaultEffectsSpec()))
            },
            // 4. Define Predictive Back animations (Android 14+)
            predictivePopTransitionSpec = {
                fadeIn(motionScheme.defaultEffectsSpec())
                    .togetherWith(fadeOut(motionScheme.defaultEffectsSpec()))
            },
            entryProvider = { key ->
                when (key) {
                    is Route.HomeScreen -> {
                        NavEntry(key) {
                            HomeScreen(
                                innerPadding = innerPadding,
                                onNavigate = { route -> backStack.add(route) }
                            )
                        }
                    }

                    is Route.AnalyticsScreen -> {
                        NavEntry(key) {
                            AnalyticsScreen(innerPadding = innerPadding)
                        }
                    }

                    is Route.SettingsScreen -> {
                        NavEntry(key) {
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
                        NavEntry(key) {
                            AddSubscriptionScreen(
                                innerPadding = innerPadding,
                                onNavigate = { route -> backStack.add(route) }
                            )
                        }
                    }

                    is Route.SubscriptionDetailsScreen -> {
                        NavEntry(key) {
                            SubscriptionDetailsScreen(
                                innerPadding = innerPadding,
                                subscriptionId = key.subscriptionId,
                                onBackClick = { backStack.removeAt(backStack.lastIndex) },
                                onEditClick = { id -> backStack.add(Route.EditSubscriptionScreen(id)) }
                            )
                        }
                    }

                    is Route.EditSubscriptionScreen -> {
                        NavEntry(key) {
                            EditSubscriptionScreen(
                                subscriptionId = key.subscriptionId,
                                innerPaddingValues = innerPadding,
                                onBackClick = { backStack.removeAt(backStack.lastIndex) },
                                onSaveSuccess = {
                                    // Remove Edit Screen, landing back on Details (which reloads)
                                    backStack.removeAt(backStack.lastIndex)
                                }
                            )
                        }
                    }

                    is Route.OnboardingScreen -> {
                        NavEntry(key) {
                            OnboardingScreen(
                                onOnboardComplete = { budget, currency, route ->
                                    onboardingViewModel.saveBudget(
                                        budget = budget,
                                        currency = currency,
                                        onSuccess = {
                                            Log.d("NavGraph", "Onboarding complete, navigating to $route")
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
                        NavEntry(key) {
                            AddSubscriptionDetailsScreen(
                                innerPaddingValues = innerPadding,
                                name = key.name,
                                iconResId = key.iconRes,
                                onBackClick = { backStack.removeAt(backStack.lastIndex) },
                                onSaveSuccess = {
                                    // Pop back until HomeScreen
                                    while (backStack.last() !is Route.HomeScreen) {
                                        backStack.removeAt(backStack.lastIndex)
                                    }
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