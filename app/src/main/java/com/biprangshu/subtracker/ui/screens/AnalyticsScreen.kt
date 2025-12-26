package com.biprangshu.subtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biprangshu.subtracker.ui.components.MonthlySpendChart
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore

@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    // 1. Setup Vico Model Producer
    val modelProducer = remember { CartesianChartModelProducer() }
    val modelProducer2 = remember { CartesianChartModelProducer() }
    val labelKey = remember { ExtraStore.Key<List<String>>() }

    // 2. Generate Dummy Data (Jan - Dec)
    LaunchedEffect(Unit) {
        // 4. Correct Month Labels
        val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        val values = listOf(420, 480, 350, 600, 650, 500, 480, 550, 400, 750, 850, 947)

        modelProducer.runTransaction {
            columnSeries { series(values) }
            extras { it[labelKey] = months }
        }

        val subscriptions = listOf("Netflix", "Spotify", "Disney+", "YouTube")
        val costs = listOf(9.99, 4.99, 14.99, 11.99)

        modelProducer2.runTransaction {
            columnSeries { series(costs) }
            extras { it[labelKey] = subscriptions }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    // Add bottom padding for the list/content so it doesn't get hidden behind bottom bar
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState()), // Make the whole screen scrollable
        ) {
            Text(
                "Analytics",
                style = TextStyle(
                    fontFamily = robotoFlexTopBar,
                    fontSize = 32.sp,
                    lineHeight = 34.sp,
                    color = colorScheme.primary
                ),
            )
            Spacer(Modifier.height(24.dp))

            // 3. "Your Spends" Card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.6f)
                ),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Your Spends",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4. The Chart
                    MonthlySpendChart(
                        modelProducer = modelProducer,
                        xValueFormatter = CartesianValueFormatter { context, x, _ ->
                            // Map index to Month String from ExtraStore
                            val index = x.toInt()
                            context.model.extraStore[labelKey].getOrElse(index) { "" }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(28.dp))
            //subscription spend chart
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.6f)
                ),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Your Subscription Spends",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4. The Chart
                    MonthlySpendChart(
                        modelProducer = modelProducer2,
                        xValueFormatter = CartesianValueFormatter { context, x, _ ->
                            // Map index to Month String from ExtraStore
                            val index = x.toInt()
                            context.model.extraStore[labelKey].getOrElse(index) { "" }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            //ai card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.6f)
                ),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier= Modifier.fillMaxWidth().padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Ask more of your spending with AI",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        )
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}