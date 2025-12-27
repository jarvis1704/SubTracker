package com.biprangshu.subtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import com.biprangshu.subtracker.navigation.NavGraph
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.ui.components.Fab
import com.biprangshu.subtracker.ui.components.SubTrackerBottomAppBar
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.AnalyticsScreen
import com.biprangshu.subtracker.ui.screens.Settings.SettingsScreen
import com.biprangshu.subtracker.ui.theme.SubTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SubTrackerTheme {

                //navigation 3 backstack
                val backStack = rememberNavBackStack(
                    Route.HomeScreen
                )

                BackHandler(
                    enabled = backStack.size > 1
                ) {
                    backStack.removeAt(backStack.lastIndex)
                }

                val currentRoute = backStack.lastOrNull() as? Route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        SubTrackerBottomAppBar(
                            currentRoute= currentRoute,
                            onNavigate = {
                                route->

                                //prevents duplicate navigation
                                if(currentRoute == route) return@SubTrackerBottomAppBar

                                //pop everything up to root (only for bottom bar navigation)
                                while (backStack.size > 1){
                                    backStack.removeAt(backStack.lastIndex)
                                }


                                //navigation logic, only navigate when not on the current screen
                                if(route != Route.HomeScreen){
                                    //add route to backstack
                                    backStack.add(route)
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        Fab() {
                            //onclick redirection
                        }
                    }

                ) { innerPadding ->
//                    HomeScreen(
//                        innerPadding = innerPadding
//                    )
//                    AnalyticsScreen(
//                        innerPadding = innerPadding
//                    )
//                    SettingsScreen(
//                        innerPadding = innerPadding
//                    )

                    NavGraph(
                        backStack= backStack,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

