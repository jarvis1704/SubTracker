package com.biprangshu.subtracker.ui.screens.Settings.screens

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.biprangshu.subtracker.ui.screens.Settings.components.SettingsItem
import com.biprangshu.subtracker.ui.screens.Settings.components.SwitchSettingsItem
import com.biprangshu.subtracker.ui.screens.Settings.viewmodel.NotificationSettingsViewModel
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NotificationSettingsScreen(
    onBack: () -> Unit,
    viewModel: NotificationSettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val masterEnabled by viewModel.isMasterEnabled.collectAsState()
    val paymentReminders by viewModel.isPaymentRemindersEnabled.collectAsState()


    val topItemShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    val middleItemShape = RoundedCornerShape(4.dp)
    val bottomItemShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
    val singleItemShape = RoundedCornerShape(24.dp)

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Notifications", style = TextStyle(fontFamily = robotoFlexTopBar, fontSize = 32.sp)) },
                navigationIcon = {
                    FilledTonalIconButton(onClick = onBack, shapes = IconButtonDefaults.shapes()) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 1. Master Toggle (In-App)
            SwitchSettingsItem(
                icon = if (masterEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                title = "Allow Notifications",
                subtitle = "Master switch for all app alerts",
                checked = masterEnabled,
                onCheckedChange = { viewModel.toggleMaster(it) },
                shape = singleItemShape
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (masterEnabled) {
                Text(
                    "Notification Categories",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    SwitchSettingsItem(
                        icon = Icons.Default.Payment,
                        title = "Payment Reminders",
                        subtitle = "Alerts for upcoming bills",
                        checked = paymentReminders,
                        onCheckedChange = { viewModel.togglePaymentReminders(it) },
                        shape = singleItemShape
                    )
                    // Future notification categories can be added here
                }

                Spacer(modifier = Modifier.height(24.dp))
            }


            Text(
                "System Settings",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
            )

            ListItem(
                headlineContent = { Text("Android Notification Settings") },
                supportingContent = { Text("Manage sound, vibration, and priority") },
                leadingContent = {
                    Icon(Icons.Default.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                },
                trailingContent = {
                    Icon(
                        Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                modifier = Modifier
                    .clip(singleItemShape)
                    .clickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                            }
                            context.startActivity(intent)
                        } else {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = android.net.Uri.parse("package:${context.packageName}")
                            }
                            context.startActivity(intent)
                        }
                    }
            )
        }
    }
}