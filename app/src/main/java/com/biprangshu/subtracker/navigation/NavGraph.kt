package com.biprangshu.subtracker.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.biprangshu.subtracker.ui.screens.Settings.screens.AISettingsScreen
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

    fun isMainTab(key: Any?): Boolean {
        return when (key) {
            is Route.HomeScreen,
            is Route.AnalyticsScreen,
            is Route.SettingsScreen -> true
            else -> false
        }
    }

    SharedTransitionLayout(modifier = modifier) {
        NavDisplay(
            backStack = backStack,
            onBack = {
                if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
            },

            //forward navigation
            transitionSpec = {

                val fromKey = initialState.key
                val toKey = targetState.key

                if (isMainTab(fromKey) && isMainTab(toKey)) {
                    EnterTransition.None togetherWith ExitTransition.None
                } else {
                    (slideInHorizontally(initialOffsetX = { it }) + fadeIn())
                        .togetherWith(slideOutHorizontally(targetOffsetX = { -it / 4 }) + fadeOut())
                }
            },

            //back navigation
            popTransitionSpec = {
                val fromKey = initialState.key
                val toKey = targetState.key

                if (isMainTab(fromKey) && isMainTab(toKey)) {
                    EnterTransition.None togetherWith ExitTransition.None
                } else {
                    (slideInHorizontally(initialOffsetX = { -it / 4 }) + fadeIn())
                        .togetherWith(slideOutHorizontally(targetOffsetX = { it }) + fadeOut())
                }
            },

            //predictive back gesture navigation
            predictivePopTransitionSpec = {
                val fromKey = initialState.key
                val toKey = targetState.key

                if (isMainTab(fromKey) && isMainTab(toKey)) {
                    EnterTransition.None togetherWith ExitTransition.None
                } else {
                    (slideInHorizontally(initialOffsetX = { -it / 4 }) + fadeIn())
                        .togetherWith(slideOutHorizontally(targetOffsetX = { it }) + fadeOut())
                }
            },

            entryProvider = { key ->
                when (key) {
                    is Route.HomeScreen -> {
                        NavEntry(key) {
                            HomeScreen(
                                innerPadding = innerPadding,
                                onNavigate = { route -> backStack.add(route) },
                                sharedTransitionScope = this@SharedTransitionLayout
                            )
                        }
                    }
                    is Route.AnalyticsScreen -> {
                        NavEntry(key) {
                            AnalyticsScreen(
                                innerPadding = innerPadding,
                                onNavigate = { route -> backStack.add(route) }
                            )
                        }
                    }
                    is Route.SettingsScreen -> {
                        NavEntry(key) {
                            SettingsScreen(
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
                                onEditClick = { id -> backStack.add(Route.EditSubscriptionScreen(id)) },
                                sharedTransitionScope = this@SharedTransitionLayout
                            )
                        }
                    }
                    is Route.AddSubscriptionScreen -> {
                        NavEntry(key) {
                            AddSubscriptionScreen(
                                innerPadding = innerPadding,
                                onNavigate = { route -> backStack.add(route) },
                                onBackClick = { backStack.removeAt(backStack.lastIndex) }
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
                                color = key.colour,
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

                    is Route.AISettingsScreen -> {
                        NavEntry(key) {
                            AISettingsScreen(
                                onBack = { backStack.removeAt(backStack.lastIndex) }
                            )
                        }
                    }

                    else -> error("Unknown route: $key")
                }
            }
        )
    }
}