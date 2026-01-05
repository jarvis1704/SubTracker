package com.biprangshu.subtracker.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object HomeScreen: Route, NavKey

    @Serializable
    data object AnalyticsScreen: Route, NavKey

    @Serializable
    data object SettingsScreen: Route, NavKey

    @Serializable
    data object AddSubscriptionScreen: Route, NavKey

    @Serializable
    data object SubscriptionDetailsScreen: Route, NavKey

    @Serializable
    data object OnboardingScreen: Route, NavKey

    @Serializable
    data object AddSubscriptionDetailsScreen: Route, NavKey
}