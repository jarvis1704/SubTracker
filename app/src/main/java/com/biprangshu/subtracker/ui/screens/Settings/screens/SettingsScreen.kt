// [file: subtracker/ui/screens/Settings/screens/SettingsScreen.kt]
package com.biprangshu.subtracker.ui.screens.Settings.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.ui.screens.Settings.components.SettingsItem
import com.biprangshu.subtracker.ui.screens.Settings.components.SwitchSettingsItem
import com.biprangshu.subtracker.ui.screens.Settings.viewmodel.SettingsScreenViewModel
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    settingsScreenViewModel: SettingsScreenViewModel = hiltViewModel(),
    onNavigate: (Route) -> Unit = {}
) {
    // Defines the strict visual grouping style from the screenshot
    val topItemShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    val middleItemShape = RoundedCornerShape(4.dp)
    val bottomItemShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
    val singleItemShape = RoundedCornerShape(24.dp)

    val isBiometricEnabled by settingsScreenViewModel.isBiometricEnabled.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() + 16.dp),
        ) {
            // Static Header
            Text(
                "Settings",
                style = TextStyle(
                    fontFamily = robotoFlexTopBar,
                    fontSize = 32.sp,
                    lineHeight = 34.sp,
                    color = colorScheme.primary
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    bottom = innerPadding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Spacing between "Cards"
            ) {
                // Group 1: AI Features (The "Hero" feature set)
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        SettingsItem(
                            icon = Icons.Default.AutoAwesome,
                            title = "AI Features",
                            subtitle = "Optimization, Burn Rate & Periodicity",
                            shape = singleItemShape,
                            onClick = { onNavigate(Route.AISettingsScreen) }
                        )
                    }
                }

                // Group 2: Notifications & Security (Functional Settings)
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        SettingsItem(
                            icon = Icons.Default.Notifications,
                            title = "Notifications",
                            subtitle = "Manage alerts",
                            shape = topItemShape,
                            onClick = {
                                onNavigate(Route.NotificationSettingsScreen)
                            }
                        )
                        SwitchSettingsItem(
                            icon = Icons.Default.Lock,
                            title = "Security",
                            subtitle = "Biometric Authentication",
                            shape = bottomItemShape,
                            checked = isBiometricEnabled,
                            onCheckedChange = { settingsScreenViewModel.toggleBiometric(it) }
                        )
                    }
                }

                // Group 3: About
                item {
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "About SubTracker",
                        subtitle = "Version 1.0.0",
                        shape = singleItemShape,
                        onClick = { onNavigate(Route.AboutScreen) }
                    )
                }
            }
        }
    }
}