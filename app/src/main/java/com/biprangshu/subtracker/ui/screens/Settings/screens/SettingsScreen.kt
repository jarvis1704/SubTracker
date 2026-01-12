package com.biprangshu.subtracker.ui.screens.Settings.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
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
import com.biprangshu.subtracker.BuildConfig
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.ui.screens.Settings.viewmodel.SettingsScreenViewModel
import com.biprangshu.subtracker.ui.screens.Settings.components.ProfileCard
import com.biprangshu.subtracker.ui.screens.Settings.components.SettingsItem
import com.biprangshu.subtracker.ui.screens.Settings.components.SwitchSettingsItem
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    settingsScreenViewModel: SettingsScreenViewModel = hiltViewModel(),
    onNavigate: (Route) -> Unit = {}
) {
    // Shapes for grouped list items
    val topItemShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    val middleItemShape = RoundedCornerShape(4.dp)
    val bottomItemShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
    val singleItemShape = RoundedCornerShape(24.dp)

    val isBiometicEnabled by settingsScreenViewModel.isBiometricEnabled.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.surface // Base background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() + 16.dp),
        ) {
            // Header
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    ProfileCard()
                }

                item { Spacer(modifier = Modifier.height(12.dp)) }

                // 2. Appearance Group
                item {
                    Text(
                        "Appearance",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorScheme.primary,
                        modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        SettingsItem(
                            icon = Icons.Default.Palette,
                            title = "Theme",
                            subtitle = "System Default",
                            shape = topItemShape,
                            onClick = {}
                        )
                        SettingsItem(
                            icon = Icons.Default.DarkMode,
                            title = "Dark Mode",
                            subtitle = "Follow System",
                            shape = bottomItemShape,
                            onClick = {}
                        )
                    }
                }

                // 3. General Group
                item {
                    Text(
                        "General",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorScheme.primary,
                        modifier = Modifier.padding(start = 12.dp, bottom = 8.dp, top = 8.dp)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        SettingsItem(
                            icon = Icons.Default.Notifications,
                            title = "Notifications",
                            subtitle = "Manage alerts",
                            shape = topItemShape,
                            onClick = {}
                        )
                        SwitchSettingsItem(
                            icon = Icons.Default.Lock,
                            title = "Security",
                            subtitle = "Biometric Authentication",
                            shape = bottomItemShape,
                            checked = isBiometicEnabled,
                            onCheckedChange = { settingsScreenViewModel.toggleBiometric(it) }
                        )
                    }
                }

                // 4. About Group (Single Item)
                item {
                    Spacer(Modifier.height(8.dp))
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "About SubTracker",
                        subtitle = "Version ${BuildConfig.VERSION_NAME}",
                        shape = singleItemShape,
                        onClick = {
                            onNavigate(Route.AboutScreen)
                        }
                    )
                }
            }
        }
    }
}



