package com.biprangshu.subtracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation3.runtime.rememberNavBackStack
import com.biprangshu.subtracker.domain.repository.UserPreferencesRepository
import com.biprangshu.subtracker.navigation.NavGraph
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.ui.components.Fab
import com.biprangshu.subtracker.ui.components.SubTrackerBottomAppBar
import com.biprangshu.subtracker.ui.screens.onboarding.viewmodel.OnboardingViewModel
import com.biprangshu.subtracker.ui.theme.SubTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    private var isBiometricAuthenticated by mutableStateOf(false)
    private var isBiometricEnabled by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {


        val splashScreen = installSplashScreen()


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //biometric
        lifecycleScope.launch {
            isBiometricEnabled = userPreferencesRepository.isBiometricEnabledFlow.first()

            if (isBiometricEnabled) {
                showBiometricPrompt()
            } else {
                isBiometricAuthenticated = true
            }
        }

        splashScreen.setKeepOnScreenCondition {
            !isAppReady || (isBiometricEnabled && !isBiometricAuthenticated)
        }

        setContent {
            SubTrackerTheme {

                val onboardingViewModel: OnboardingViewModel = hiltViewModel()
                val context = LocalContext.current

                //notification permision request
                val notificationPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (!isGranted) {
                            Toast.makeText(context, "Notifications disabled. You won't receive reminders.", Toast.LENGTH_LONG).show()
                        }
                    }
                )

                //to check if it has the permissions
                LaunchedEffect(isAppReady, isBiometricAuthenticated) {
                    if (isAppReady && (!isBiometricEnabled || isBiometricAuthenticated)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val hasPermission = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED

                            if (!hasPermission) {
                                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }
                    }
                }

                if(isAppReady && (!isBiometricEnabled || isBiometricAuthenticated)){
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

    //function to facilitate biometric authentication
    private fun showBiometricPrompt() {
        val executor: Executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    isBiometricAuthenticated = true
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON || errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                        finish()
                    }
                    Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verify it's you")
            .setNegativeButtonText("Exit")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }



}

