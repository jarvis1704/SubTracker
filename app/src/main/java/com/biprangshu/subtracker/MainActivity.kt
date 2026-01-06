package com.biprangshu.subtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.biprangshu.subtracker.navigation.NavGraph
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.ui.components.Fab
import com.biprangshu.subtracker.ui.components.SubTrackerBottomAppBar
import com.biprangshu.subtracker.ui.screens.onboarding.viewmodel.OnboardingViewModel
import com.biprangshu.subtracker.ui.theme.SubTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition {
            !isAppReady
        }

        setContent {
            SubTrackerTheme {

                val onboardingViewModel: OnboardingViewModel = hiltViewModel()

                if(isAppReady){
                    //navigation 3 backstack
                    val backStack = rememberNavBackStack(
                        if (showOnboardingScreens){
                            Route.OnboardingScreen
                        }else{
                            Route.HomeScreen
                        }
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
                            if(
                                currentRoute == Route.HomeScreen ||
                                currentRoute == Route.AnalyticsScreen ||
                                currentRoute == Route.SettingsScreen
                            ){
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
                            }
                        },
                        floatingActionButton = {
                            if (
                                currentRoute == Route.HomeScreen
                            ){
                                Fab(){
                                        route ->
                                    //navigate to add subscription screen
                                    backStack.add(route)
                                }
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

//                    SubscriptionDetailsScreen(
//                        innerPadding = innerPadding
//                    )

                        NavGraph(
                            backStack= backStack,
                            innerPadding = innerPadding,
                            onboardingViewModel = onboardingViewModel
                        )

                    }
                }



            }
        }
    }
}

