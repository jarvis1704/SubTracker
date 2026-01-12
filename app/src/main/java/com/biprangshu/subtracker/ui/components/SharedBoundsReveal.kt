package com.biprangshu.subtracker.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.OverlayClip
import androidx.compose.animation.SharedTransitionScope.ResizeMode.Companion.scaleToBounds
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.navigation3.ui.LocalNavAnimatedContentScope

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedBoundsReveal(
    sharedContentState: SharedTransitionScope.SharedContentState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope? = LocalNavAnimatedContentScope.current,

    boundsTransform: BoundsTransform = BoundsTransform { _, _ ->
        spring(
            dampingRatio = 0.8f,
            stiffness = 350f,
            visibilityThreshold = Rect.VisibilityThreshold
        )
    },

    enter: EnterTransition = fadeIn(
        animationSpec = tween(durationMillis = 600)
    ),

    exit: ExitTransition = fadeOut(
        animationSpec = tween(durationMillis = 600)
    ),
    resizeMode: SharedTransitionScope.ResizeMode = scaleToBounds(
        contentScale = ContentScale.Crop
    ),
    clipShape: Shape = MaterialTheme.shapes.large,
    renderInOverlayDuringTransition: Boolean = true,
): Modifier =
    with(sharedTransitionScope) {
        if (animatedVisibilityScope == null) return@with this@sharedBoundsReveal

        this@sharedBoundsReveal.sharedBounds(
            sharedContentState = sharedContentState,
            animatedVisibilityScope = animatedVisibilityScope,
            boundsTransform = boundsTransform,
            enter = enter,
            exit = exit,
            resizeMode = resizeMode,
            clipInOverlayDuringTransition = OverlayClip(clipShape),
            renderInOverlayDuringTransition = renderInOverlayDuringTransition,
        )
    }