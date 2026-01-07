package com.biprangshu.subtracker.ui.screens.AnalyticsScreen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BudgetSpendCard(
    spent: Float,
    budget: Float
) {
    val progress = (spent / budget).coerceIn(0f, 1f)
    // Smooth animation for the progress bar
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000),
        label = "BudgetProgress"
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.secondaryContainer
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Monthly Budget",
                        style = MaterialTheme.typography.labelLarge,
                        color = colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$${spent.toInt()}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "/ $${budget.toInt()}",
                            style = MaterialTheme.typography.titleMedium,
                            color = colorScheme.onSecondaryContainer.copy(alpha = 0.6f),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                // Percent Text
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp) // Expressive thickness
                    .clip(RoundedCornerShape(16.dp)), // Ensures track is also rounded
                color = colorScheme.primary,
                trackColor = colorScheme.surface.copy(alpha = 0.5f),
                strokeCap = StrokeCap.Square,
                gapSize = 4.dp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$${(budget - spent).toInt()} left to spend",
                style = MaterialTheme.typography.labelMedium,
                color = colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}