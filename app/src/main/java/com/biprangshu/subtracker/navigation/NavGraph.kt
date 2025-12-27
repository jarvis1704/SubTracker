package com.biprangshu.subtracker.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.AnalyticsScreen
import com.biprangshu.subtracker.ui.screens.HomeScreen.HomeScreen
import com.biprangshu.subtracker.ui.screens.Settings.SettingsScreen
import com.biprangshu.subtracker.ui.screens.addsubscriptionscreen.AddSubscriptionScreen
import com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.SubscriptionDetailsScreen

@Composable
fun NavGraph(
    backStack: MutableList<NavKey>,
    modifier: Modifier = Modifier,
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
                            innerPadding = innerPadding
                        )
                    }
                }

                is Route.AddSubscriptionScreen -> {
                    NavEntry(key){
                        AddSubscriptionScreen(
                            innerPadding = innerPadding
                        )
                    }
                }

                is Route.SubscriptionDetailsScreen -> {
                    NavEntry(key){
                        SubscriptionDetailsScreen(
                            innerPadding = innerPadding,
                            onBackClick = {
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