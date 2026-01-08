package com.biprangshu.subtracker.ui.screens.AnalyticsScreen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.components.BudgetSpendCard
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.viewmodel.AnalysisScreenViewModel
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    analysisScreenViewModel: AnalysisScreenViewModel = hiltViewModel()
) {
    //vico models
    val modelProducer = remember { CartesianChartModelProducer() }
    val modelProducer2 = remember { CartesianChartModelProducer() }
    val labelKey = remember { ExtraStore.Key<List<String>>() }

    val userData by analysisScreenViewModel.userData.collectAsState()
    val totalMonthlySpend by analysisScreenViewModel.totalMonthlySpend.collectAsState()
    val monthlyChartData by analysisScreenViewModel.monthlyChartData.collectAsState()
    val subscriptionBreakdown by analysisScreenViewModel.subscriptionBreakdownData.collectAsState()

    val budget = userData?.budget?.toFloat() ?: 500f
    val monthlySpend = totalMonthlySpend.toFloat()

    // 2. Generate Dummy Data (Jan - Dec)
    //todo: replace with real data for graphs
    LaunchedEffect(monthlyChartData) {

        val months = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        modelProducer.runTransaction {
            columnSeries {
                series(monthlyChartData)
            }
            extras {
                it[labelKey] = months
            }
        }
    }

    LaunchedEffect(subscriptionBreakdown) {

        val names = subscriptionBreakdown.map { it.name }
        val costs = subscriptionBreakdown.map { it.price }

        if(names.isNotEmpty()){
            modelProducer2.runTransaction {
                columnSeries {
                    series(costs)
                }
                extras {
                    it[labelKey] = names
                }
            }
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


            BudgetSpendCard(
                spent = monthlySpend,
                budget = budget
            )

            Spacer(Modifier.height(28.dp))

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