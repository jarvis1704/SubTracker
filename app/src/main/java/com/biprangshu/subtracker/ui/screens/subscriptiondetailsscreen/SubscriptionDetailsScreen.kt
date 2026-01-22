package com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.biprangshu.subtracker.ui.components.sharedBoundsReveal
import com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.components.SubscriptionHeroCard
import com.biprangshu.subtracker.ui.screens.subscriptiondetailsscreen.viewmodel.SubscriptionDetailsViewModel
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar
import com.biprangshu.subtracker.domain.model.Subscription
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SubscriptionDetailsScreen(
    subscriptionId: Int,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onBackClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: SubscriptionDetailsViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope
) {
    LaunchedEffect(subscriptionId) {
        viewModel.loadSubscription(subscriptionId)
    }

    val subscription by viewModel.subscription.collectAsState()
    val priceAlert by viewModel.priceAlert.collectAsState()

    var showDeleteSheet by remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val topItemShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    val middleItemShape = RoundedCornerShape(4.dp)
    val bottomItemShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)

    Scaffold(
        modifier = modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeFlexibleTopAppBar(
                title = {
                    Text(
                        subscription?.name ?: "Details",
                        style = TextStyle(fontFamily = robotoFlexTopBar, fontSize = 32.sp)
                    )
                },
                navigationIcon = {
                    FilledTonalIconButton(onClick = onBackClick, shapes = IconButtonDefaults.shapes()) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    FilledTonalIconButton(onClick = { onEditClick(subscriptionId) }, IconButtonDefaults.shapes()) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
    ) { scaffoldPadding ->
        subscription?.let { sub ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(scaffoldPadding)
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier.sharedBoundsReveal(
                        sharedTransitionScope = sharedTransitionScope,
                        sharedContentState = sharedTransitionScope.rememberSharedContentState(key = "subscription-${sub.id}")
                    )
                ) {
                    SubscriptionHeroCard(subscription = sub)
                }

                // AI Alert Section
                priceAlert?.let { alert ->
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "AI Analysis",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                    )
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.onErrorContainer)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Price Hike Detected",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = alert.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    DetailItem(Icons.Default.Movie, "Category", sub.category ?: "Uncategorized", topItemShape)
                    DetailItem(Icons.Default.DateRange, "Billing Cycle", sub.billingCycle, middleItemShape)
                    DetailItem(Icons.Default.CreditCard, "Payment Method", sub.paymentMethod ?: "Not set", middleItemShape)
                    DetailItem(
                        Icons.Default.Notifications,
                        "Reminder",
                        if(sub.remindersEnabled) "${sub.reminderDaysBefore} days before" else "Disabled",
                        bottomItemShape
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Recent Payments",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                )

                CalculatedSpendingHistory(subscription = sub)

                Spacer(modifier = Modifier.height(32.dp))

                // Delete Button
                FilledTonalButton(
                    onClick = { showDeleteSheet = true },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Delete Subscription", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding() + 24.dp))
            }
        }
    }


    if (showDeleteSheet) {
        ModalBottomSheet(
            onDismissRequest = { showDeleteSheet = false },
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Delete Subscription?",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Are you sure you want to remove ${subscription?.name}? This action cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(Modifier.height(32.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                    OutlinedButton(
                        onClick = { showDeleteSheet = false },
                        modifier = Modifier.weight(1f).height(48.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Text("Cancel")
                    }


                    Button(
                        onClick = {
                            viewModel.deleteSubscription {
                                showDeleteSheet = false
                                onBackClick()
                            }
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatedSpendingHistory(subscription: Subscription) {
    val payments = remember(subscription) {
        val list = mutableListOf<Long>()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = subscription.firstPaymentDate
        val now = System.currentTimeMillis()

        while (calendar.timeInMillis <= now) {
            list.add(calendar.timeInMillis)
            if (subscription.billingCycle == "Monthly") {
                calendar.add(Calendar.MONTH, 1)
            } else {
                calendar.add(Calendar.YEAR, 1)
            }
        }
        list.takeLast(6)
    }

    if (payments.isEmpty()) {
        Text(
            "No payments made yet.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp)
        )
        return
    }

    val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())

    Row(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        payments.forEach { dateMillis ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.height(100.dp)
            ) {
                Text(
                    text = dateFormat.format(Date(dateMillis)),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(42.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = "${subscription.price.toInt()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailItem(icon: ImageVector, label: String, value: String, shape: androidx.compose.ui.graphics.Shape) {
    ListItem(
        headlineContent = { Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        supportingContent = { Text(value, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface) },
        leadingContent = { Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        modifier = Modifier.clip(shape)
    )
}