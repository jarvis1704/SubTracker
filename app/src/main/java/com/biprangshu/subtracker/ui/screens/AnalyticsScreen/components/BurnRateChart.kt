package com.biprangshu.subtracker.ui.screens.AnalyticsScreen.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.shape.CorneredShape

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BurnRateChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
    xValueFormatter: CartesianValueFormatter,
    currency: String,
    animationSpec: AnimationSpec<Float>? = motionScheme.defaultEffectsSpec()
) {
    val currencyFormatter = CartesianValueFormatter { _, value, _ ->
        "${currency}${value.toInt()}"
    }

    ProvideVicoTheme(rememberM3VicoTheme()) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    // Define lines: 1. Predicted (Primary), 2. Average (Secondary/Dashed)
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        // Line 1: Actual Burn Rate (Spikes)
                        LineCartesianLayer.Line(
                            fill = LineCartesianLayer.LineFill.single(fill(colorScheme.primary)),
//                            areaFill = LineCartesianLayer.AreaFill.single(
//                                fill(
//                                    DynamicShaders.verticalGradient(
//                                        arrayOf(colorScheme.primary.copy(alpha = 0.4f).toArgb(), Color.Transparent.toArgb())
//                                    )
//                                )
//                            )
                        ),
                        // Line 2: Average Baseline
                        LineCartesianLayer.Line(
                            fill = LineCartesianLayer.LineFill.single(fill(colorScheme.tertiary)),
                        )
                    )
                ),
                startAxis = VerticalAxis.rememberStart(
                    label = rememberTextComponent(colorScheme.onSurfaceVariant),
                    valueFormatter = currencyFormatter,
                    line = rememberLineComponent(Fill.Transparent)
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    label = rememberTextComponent(colorScheme.onSurfaceVariant),
                    valueFormatter = xValueFormatter,
                    line = rememberLineComponent(Fill.Transparent)
                )
            ),
            modelProducer = modelProducer,
            modifier = modifier.height(280.dp),
            animationSpec = animationSpec
        )
    }
}