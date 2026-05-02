package com.biprangshu.subtracker.feature.analytics.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.Fill

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

                    lineProvider = LineCartesianLayer.LineProvider.series(

                        LineCartesianLayer.Line(
                            fill = LineCartesianLayer.LineFill.single(fill(colorScheme.primary)),
                        ),

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
