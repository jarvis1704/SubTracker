package com.biprangshu.subtracker.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Data class to represent a single item in the bottom navigation bar.
 */
data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

/**
 * List of navigation items based on the provided design.
 * We use 'Filled' icons for the selected state and 'Outlined' for the unselected state.
 */
val bottomNavItems = listOf(
    BottomNavItem(
        label = "Dashboard",
        selectedIcon = Icons.Filled.Dashboard,
        unselectedIcon = Icons.Outlined.Dashboard
    ),
    BottomNavItem(
        label = "Analytics",
        selectedIcon = Icons.Filled.Analytics,
        unselectedIcon = Icons.Outlined.Analytics
    ),
    BottomNavItem(
        label = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)

/**
 * A Material 3 expressive bottom navigation bar with animated icons.
 *
 * @param modifier The modifier to be applied to the NavigationBar.
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SubTrackerBottomAppBar(modifier: Modifier = Modifier) {
    // State to keep track of the currently selected item index.
    // Since there is no navigation graph yet, we manage this state locally.
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    // This Box replicates the positioning seen in the demo app's Scaffold bottomBar
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 42.dp), // Added padding to float it off the bottom edge
        contentAlignment = Alignment.Center
    ) {
        HorizontalFloatingToolbar(
            expanded = true,
            // Uses the 'vibrant' colors from the demo app
            colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(
                toolbarContainerColor = MaterialTheme.colorScheme.surfaceContainer, // Adjusted for standard M3
                toolbarContentColor = MaterialTheme.colorScheme.onSurface
            ),
            content = {
                bottomNavItems.forEachIndexed { index, item ->
                    val isSelected = selectedItemIndex == index

                    ToggleButton(
                        checked = isSelected,
                        onCheckedChange = { selectedItemIndex = index },
                        // Styling matches the demo app's usage
                        colors = ToggleButtonDefaults.toggleButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            checkedContainerColor = MaterialTheme.colorScheme.primary,
                            checkedContentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        // CircleShape is used for all corners in the demo app
                        shapes = ToggleButtonDefaults.shapes(
                            CircleShape,
                            CircleShape,
                            CircleShape
                        ),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            // Icon Animation
                            Crossfade(targetState = isSelected, label = "IconFade") { selected ->
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            }

                            // Text Expansion Animation like in the demo
                            AnimatedVisibility(
                                visible = isSelected,
                                enter = expandHorizontally(),
                                exit = shrinkHorizontally()
                            ) {
                                Text(
                                    text = item.label,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Clip,
                                    modifier = Modifier.padding(start = ButtonDefaults.IconSpacing)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}