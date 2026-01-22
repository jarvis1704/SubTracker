package com.biprangshu.subtracker.ui.screens.Settings.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.biprangshu.subtracker.ui.screens.Settings.components.SwitchSettingsItem
import com.biprangshu.subtracker.ui.screens.Settings.viewmodel.AISettingsViewModel
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AISettingsScreen(
    onBack: () -> Unit,
    viewModel: AISettingsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val hapticFeedback = LocalHapticFeedback.current

    val optimizerEnabled by viewModel.isOptimizerEnabled.collectAsState()
    val burnRateEnabled by viewModel.isBurnRateEnabled.collectAsState()
    val priceAlertsEnabled by viewModel.isPriceAlertsEnabled.collectAsState()
    val days by viewModel.periodicityDays.collectAsState()

    // Shapes for grouped list items
    val topItemShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    val middleItemShape = RoundedCornerShape(4.dp)
    val bottomItemShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
    val singleItemShape = RoundedCornerShape(24.dp)

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("AI Features", style = TextStyle(fontFamily = robotoFlexTopBar, fontSize = 32.sp)) },
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

            Text(
                "Active Features",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                SwitchSettingsItem(
                    icon = Icons.Default.AutoAwesome,
                    title = "Smart Optimization",
                    subtitle = "Find bundles & redundancies",
                    checked = optimizerEnabled,
                    onCheckedChange = { viewModel.toggleOptimizer(it) },
                    shape = topItemShape
                )
                SwitchSettingsItem(
                    icon = Icons.Default.TrendingDown,
                    title = "Burn Rate Analysis",
                    subtitle = "Predict future cash flow",
                    checked = burnRateEnabled,
                    onCheckedChange = { viewModel.toggleBurnRate(it) },
                    shape = middleItemShape
                )
                SwitchSettingsItem(
                    icon = Icons.Default.PriceCheck,
                    title = "Price Watchdog",
                    subtitle = "Alert on regional price hikes",
                    checked = priceAlertsEnabled,
                    onCheckedChange = { viewModel.togglePriceAlerts(it) },
                    shape = bottomItemShape
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Analysis Frequency",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
            )

            // Frequency Slider Card
            Card(
                shape = singleItemShape,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Run analysis every", style = MaterialTheme.typography.titleMedium)
                        Text("$days Days", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(Modifier.height(12.dp))
                    Slider(
                        value = days.toFloat(),
                        onValueChange = {
                            viewModel.setPeriodicity(it.roundToInt())
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
                                        },
                        valueRange = 3f..7f,
                        steps = 3, // (7-3)/1 - 1 = 3 steps (3,4,5,6,7)
                    )
                    Text(
                        "AI tasks consume battery. Higher intervals save power.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}