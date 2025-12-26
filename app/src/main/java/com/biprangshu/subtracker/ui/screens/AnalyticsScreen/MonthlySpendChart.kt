package com.biprangshu.subtracker.ui.screens.AnalyticsScreen


import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberFadingEdges
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.ColumnCartesianLayerMarkerTarget
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.DashedShape

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MonthlySpendChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
    xValueFormatter: CartesianValueFormatter = CartesianValueFormatter.Default,
    thickness: Dp = 24.dp, // Thicker bars like in the image
    animationSpec: AnimationSpec<Float>? = motionScheme.defaultEffectsSpec()
) {
    // Formatter for Y-Axis ($ values)
    val currencyFormatter = CartesianValueFormatter { _, value, _ ->
        "$${value.toInt()}"
    }



    ProvideVicoTheme(rememberM3VicoTheme()) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberColumnCartesianLayer(
                    ColumnCartesianLayer.ColumnProvider.series(
                        vicoTheme.columnCartesianLayerColors.map { color ->
                            rememberLineComponent(
                                fill = fill(color),
                                thickness = thickness,
                                shape = CorneredShape.Pill
                            )
                        }
                    ),
                    columnCollectionSpacing = 16.dp
                ),
                startAxis = VerticalAxis.rememberStart(
                    line = rememberLineComponent(Fill.Transparent),
                    label = rememberTextComponent(colorScheme.onSurfaceVariant),
                    tick = rememberLineComponent(Fill.Transparent),
                    guideline = rememberLineComponent(
                        fill = fill(colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        shape = DashedShape(
                            shape = CorneredShape.Pill,
                            dashLengthDp = 4f,
                            gapLengthDp = 8f
                        )
                    ),
                    valueFormatter = currencyFormatter
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    line = rememberLineComponent(Fill.Transparent),
                    label = rememberTextComponent(colorScheme.onSurfaceVariant),
                    tick = rememberLineComponent(Fill.Transparent),
                    guideline = rememberLineComponent(Fill.Transparent),
                    valueFormatter = xValueFormatter
                ),
                marker = rememberDefaultCartesianMarker(
                    label = rememberTextComponent(
                        color = colorScheme.inverseOnSurface,
                        background = rememberShapeComponent(
                            fill = fill(colorScheme.inverseSurface),
                            shape = CorneredShape.rounded(8f)
                        ),
                        textSize = typography.bodySmall.fontSize,
                        padding = Insets(verticalDp = 4f, horizontalDp = 8f),
                    ),
                    valueFormatter = { _, targets ->
                        val target = targets.firstOrNull() as? ColumnCartesianLayerMarkerTarget
                        val y = target?.columns?.sumOf { it.entry.y.toDouble() } ?: 0.0
                        "$${String.format("%.2f", y)}"
                    },
                    indicator = null
                ),
                fadingEdges = rememberFadingEdges()
            ),
            modelProducer = modelProducer,

            zoomState = rememberVicoZoomState(
                zoomEnabled = true,

                initialZoom = Zoom.fixed(1.6f),
                minZoom = Zoom.Content,
            ),
            animationSpec = animationSpec,
            modifier = modifier.height(280.dp),
        )
    }
}