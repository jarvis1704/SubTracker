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
    data class SubscriptionDetailsScreen(
        val subscriptionId: Int
    ): Route, NavKey

    @Serializable
    data object OnboardingScreen: Route, NavKey

    @Serializable
    data class AddSubscriptionDetailsScreen(
        val name: String,
        val iconRes: Int,
    ): Route, NavKey

    @Serializable
    data object AboutSubscriptionScreen: Route, NavKey
}