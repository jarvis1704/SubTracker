package com.biprangshu.subtracker.ui.screens.addsubscriptionscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biprangshu.subtracker.ui.screens.addsubscriptionscreen.viewmodel.AddSubscriptionViewModel
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddSubscriptionDetailsScreen(
    modifier: Modifier = Modifier,
    name: String,
    iconResId: Int,
    innerPaddingValues: PaddingValues,
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {},
    addSubscriptionViewModel: AddSubscriptionViewModel = hiltViewModel()
) {
    // Shapes for grouped list items (Expressive Style)
    val topItemShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    val middleItemShape = RoundedCornerShape(4.dp)
    val bottomItemShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
    val singleItemShape = RoundedCornerShape(24.dp)

    // State Holders
    var currentName by remember { mutableStateOf(name) }
    var price by remember { mutableStateOf("") }
    var selectedCycleIndex by remember { mutableIntStateOf(0) } // 0: Monthly, 1: Yearly
    val cycles = listOf("Monthly", "Yearly")

    // Date Picker State
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)

    var category by remember { mutableStateOf("Entertainment") }
    var paymentMethod by remember { mutableStateOf("") }
    var remindersEnabled by remember { mutableStateOf(true) }
    var reminderDaysBefore by remember { mutableFloatStateOf(1f) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        name,
                        style = TextStyle(
                            fontFamily = robotoFlexTopBar,
                            fontSize = 32.sp
                        )
                    )
                },
                navigationIcon = {
                    FilledTonalIconButton(
                        onClick = onBackClick,
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = colorScheme.surface,
                    scrolledContainerColor = colorScheme.surfaceContainer
                )
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(scaffoldPadding)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 1. Price Input (Big & Expressive)
            Text(
                text = "Monthly Price",
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            TextField(
                value = price,
                onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) price = it },
                textStyle = MaterialTheme.typography.displayLarge.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                ),
                placeholder = {
                    Text(
                        "0.00",
                        style = MaterialTheme.typography.displayLarge.copy(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                prefix = {
                    Text(
                        //todo: change it to dynamic currency symbol based on user preference
                        "$",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface
                        )
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Plan Details Group
            Text(
                "Plan Details",
                style = MaterialTheme.typography.labelLarge,
                color = colorScheme.primary,
                modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                // Billing Cycle
                ListItem(
                    headlineContent = { Text("Billing Cycle") },
                    trailingContent = {
                        SingleChoiceSegmentedButtonRow {
                            cycles.forEachIndexed { index, label ->
                                SegmentedButton(
                                    selected = index == selectedCycleIndex,
                                    onClick = { selectedCycleIndex = index },
                                    shape = SegmentedButtonDefaults.itemShape(index = index, count = cycles.size),
                                    icon = {}
                                ) {
                                    Text(label)
                                }
                            }
                        }
                    },
                    leadingContent = {
                        Icon(Icons.Default.Repeat, contentDescription = null, tint = colorScheme.primary)
                    },
                    colors = ListItemDefaults.colors(containerColor = colorScheme.surfaceContainerHigh),
                    modifier = Modifier.clip(topItemShape)
                )

                // First Payment Date
                val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val dateString = dateFormatter.format(Date(selectedDateMillis))

                ClickableInputItem(
                    icon = Icons.Default.CalendarToday,
                    label = "First Payment",
                    value = dateString,
                    shape = middleItemShape,
                    onClick = { showDatePicker = true }
                )

                // Category (Mock Selection)
                ClickableInputItem(
                    icon = Icons.Default.Category,
                    label = "Category",
                    value = category,
                    shape = bottomItemShape,
                    onClick = { /* TODO: Open Category Sheet */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Payment & Reminders Group
            Text(
                "Preferences",
                style = MaterialTheme.typography.labelLarge,
                color = colorScheme.primary,
                modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                // Payment Method
                ListItem(
                    headlineContent = {
                        OutlinedTextField(
                            value = paymentMethod,
                            onValueChange = { paymentMethod = it },
                            placeholder = { Text("Card ending in 1234") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    leadingContent = {
                        Icon(Icons.Default.CreditCard, contentDescription = null, tint = colorScheme.primary)
                    },
                    colors = ListItemDefaults.colors(containerColor = colorScheme.surfaceContainerHigh),
                    modifier = Modifier.clip(topItemShape)
                )

                // Reminders Toggle
                ListItem(
                    headlineContent = { Text("Remind me") },
                    supportingContent = {
                        if (remindersEnabled) {
                            Text("${reminderDaysBefore.toInt()} day(s) before")
                        }
                    },
                    trailingContent = {
                        Switch(
                            checked = remindersEnabled,
                            onCheckedChange = { remindersEnabled = it }
                        )
                    },
                    leadingContent = {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = colorScheme.primary)
                    },
                    colors = ListItemDefaults.colors(containerColor = colorScheme.surfaceContainerHigh),
                    modifier = Modifier.clip(
                        if (remindersEnabled) middleItemShape else bottomItemShape
                    )
                )

                // Reminder Days Slider (Visible only if enabled)
                if (remindersEnabled) {
                    ListItem(
                        headlineContent = {
                            Column {
                                //todo: add haptic feedback to the slider
                                Slider(
                                    value = reminderDaysBefore,
                                    onValueChange = { reminderDaysBefore = it },
                                    valueRange = 1f..7f,
                                    steps = 5
                                )
                            }
                        },
                        colors = ListItemDefaults.colors(containerColor = colorScheme.surfaceContainerHigh),
                        modifier = Modifier.clip(bottomItemShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Save Button
            Button(
                onClick = {
                    //todo: add remind before thingy
                    addSubscriptionViewModel.saveSubscription(
                        name = currentName,
                        priceInput = price,
                        billingCycle = cycles[selectedCycleIndex],
                        firstPaymentDate = selectedDateMillis,
                        category = category,
                        paymentMethod = paymentMethod,
                        iconResId = iconResId,
                        iconName = name,
                        reminderEnabled = remindersEnabled,
                        reminderDaysBefore = reminderDaysBefore.toInt(),
                        onSuccess = onSaveSuccess
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(28.dp) // Expressive Pill
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Save Subscription",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Padding for bottom nav/system bars
            Spacer(modifier = Modifier.height(innerPaddingValues.calculateBottomPadding() + 24.dp))
        }

        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { selectedDateMillis = it }
                            showDatePicker = false
                        }
                    ) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
fun ClickableInputItem(
    icon: ImageVector,
    label: String,
    value: String,
    shape: androidx.compose.ui.graphics.Shape,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(label) },
        supportingContent = {
            Text(
                value,
                style = MaterialTheme.typography.titleSmall,
                color = colorScheme.primary
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorScheme.primary
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colorScheme.onSurfaceVariant
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = colorScheme.surfaceContainerHigh
        ),
        modifier = Modifier
            .clip(shape)
            .clickable { onClick() }
    )
}