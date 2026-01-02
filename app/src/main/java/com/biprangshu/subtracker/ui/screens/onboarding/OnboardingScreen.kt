package com.biprangshu.subtracker.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.biprangshu.subtracker.R
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

// Data model for the onboarding steps
data class OnboardingStep(
    val title: String,
    val description: String,
    val imageRes: Int
)

@Composable
fun OnboardingScreen(
    onGetStartedClick: () -> Unit,
    onOnboardComplete: (Route) -> Unit
) {

    val steps = listOf(
        OnboardingStep(
            title = "Track Everything",
            description = "Keep all your subscriptions in one place. Never lose track of a monthly payment again.",
            imageRes = R.drawable.onboarding1
        ),
        OnboardingStep(
            title = "Analyze Spending",
            description = "Get detailed insights into your monthly and yearly spending habits with beautiful charts.",
            imageRes = R.drawable.onboarding2
        ),
        OnboardingStep(
            title = "Get Notified",
            description = "Receive timely reminders before a subscription is due so you can decide to keep or cancel.",
            imageRes = R.drawable.onboarding3
        )
    )

    val pagerState = rememberPagerState(pageCount = { steps.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {

            //top text content
            Box(modifier = Modifier.weight(0.3f)) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(32.dp))


                    AnimatedContent(
                        targetState = steps[pagerState.currentPage].title,
                        transitionSpec = {
                            if (targetState != initialState) {
                                slideInVertically { height -> height } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                fadeIn() togetherWith fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        },
                        label = "Title Animation"
                    ) { title ->
                        Text(
                            text = title,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = robotoFlexTopBar
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    AnimatedContent(
                        targetState = steps[pagerState.currentPage].description,
                        transitionSpec = {
                            if (targetState != initialState) {
                                slideInVertically { height -> height / 2 } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height / 2 } + fadeOut()
                            } else {
                                fadeIn() togetherWith fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        },
                        label = "Desc Animation"
                    ) { desc ->
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }

            //center image content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
            ) { page ->
                val pageOffset = (
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                        ).absoluteValue

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            val scale = lerp(1f, 0.85f, pageOffset.coerceIn(0f, 1f))
                            scaleX = scale
                            scaleY = scale

                            // Fade effect
                            alpha = lerp(1f, 0.5f, pageOffset.coerceIn(0f, 1f))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = steps[page].imageRes,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            //indicators and button row
            Row(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Page Indicators
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(steps.size) { iteration ->
                        val isSelected = pagerState.currentPage == iteration
                        // Animate width of the indicator

                        val width by animateDpAsState(
                            targetValue = if(isSelected) 32.dp else 12.dp,
                            animationSpec = spring(stiffness = Spring.StiffnessMedium),
                            label = "Indicator Width"
                        )
                        val color by animateColorAsState(
                            targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                            animationSpec = spring(stiffness = Spring.StiffnessMedium),
                            label = "Indicator Color"
                        )

                        Box(
                            modifier = Modifier
                                .height(12.dp)
                                .width(width)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }


                val isLastPage = pagerState.currentPage == steps.size - 1

                Button(
                    onClick = {
                        if (isLastPage) {
                            //todo: redirect to initial setup page if new user
                            onGetStartedClick()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1,
                                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                                )
                            }
                        }
                    },
                    modifier = Modifier.height(62.dp),
                    shape = CircleShape, // Pill shape
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    AnimatedVisibility(visible = !isLastPage) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next"
                        )
                    }
                    AnimatedVisibility(visible = isLastPage) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Get Started",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }

    BudgetSetModal(
        onOnboardComplete= onOnboardComplete
    )
}