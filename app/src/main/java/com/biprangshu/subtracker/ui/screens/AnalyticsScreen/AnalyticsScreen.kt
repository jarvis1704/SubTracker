package com.biprangshu.subtracker.ui.screens.AnalyticsScreen

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.components.BudgetSpendCard
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.components.EmptyAnalyticsState
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.components.InsightCard
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.viewmodel.AnalysisScreenViewModel
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore

@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    analysisScreenViewModel: AnalysisScreenViewModel = hiltViewModel()
) {

    val userData by analysisScreenViewModel.userData.collectAsState()
    val totalMonthlySpend by analysisScreenViewModel.totalMonthlySpend.collectAsState()
    val monthlyChartData by analysisScreenViewModel.monthlyChartData.collectAsState()
    val subscriptionBreakdown by analysisScreenViewModel.subscriptionBreakdownData.collectAsState()
    val hasSubscriptions by analysisScreenViewModel.hasSubscriptions.collectAsState()

    val insights by analysisScreenViewModel.aiInsights.collectAsState()

    //vico model producer
    val modelProducer = remember { CartesianChartModelProducer() }
    val modelProducer2 = remember { CartesianChartModelProducer() }
    val labelKey = remember { ExtraStore.Key<List<String>>() }


    LaunchedEffect(monthlyChartData) {
        val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        modelProducer.runTransaction {
            columnSeries { series(monthlyChartData) }
            extras { it[labelKey] = months }
        }
    }

    LaunchedEffect(subscriptionBreakdown) {
        val names = subscriptionBreakdown.map { it.name }
        val costs = subscriptionBreakdown.map { it.price }

        if (names.isNotEmpty()) {
            modelProducer2.runTransaction {
                columnSeries { series(costs) }
                extras { it[labelKey] = names }
            }
        }
    }

    val budget = userData?.budget?.toFloat() ?: 500f
    val monthlySpend = totalMonthlySpend.toFloat()

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
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState()),
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


            BudgetSpendCard(
                spent = monthlySpend,
                budget = budget,
                currency = userData?.preferredCurrency ?: "$"
            )

            Spacer(Modifier.height(28.dp))

            if (!hasSubscriptions) {

                EmptyAnalyticsState()
            } else {

                //year Projection Chart
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.6f)
                    ),
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Yearly Projection",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        MonthlySpendChart(
                            modelProducer = modelProducer,
                            xValueFormatter = CartesianValueFormatter { context, x, _ ->
                                val index = x.toInt()
                                context.model.extraStore[labelKey].getOrElse(index) { "" }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            currency = userData?.preferredCurrency ?: "$"
                        )
                    }
                }

                Spacer(Modifier.height(28.dp))

                //subscription breakdown card
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.6f)
                    ),
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Top Subscriptions",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        MonthlySpendChart(
                            modelProducer = modelProducer2,
                            xValueFormatter = CartesianValueFormatter { context, x, _ ->
                                val index = x.toInt()
                                context.model.extraStore[labelKey].getOrElse(index) { "" }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            currency = userData?.preferredCurrency ?: "$"
                        )
                    }
                }

                Spacer(Modifier.height(28.dp))

                //ai insights
                if (insights.isNotEmpty()) {
                    Text(
                        text = "Optimization Insights",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    insights.forEach { insight ->
                        InsightCard(insight = insight)
                        Spacer(Modifier.height(12.dp))
                    }

                    Spacer(Modifier.height(16.dp))
                }

                //ai card
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.6f)
                    ),
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier.fillMaxWidth().clickable{
                        //todo: create a manual button for analysis refresh, different than this
                        analysisScreenViewModel.refreshAnalysis()
                    }
                ) {
                    Row(
                        modifier= Modifier.fillMaxWidth().padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Refresh Insights",
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
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

