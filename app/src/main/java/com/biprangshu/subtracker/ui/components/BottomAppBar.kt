package com.biprangshu.subtracker.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
@Composable
fun SubTrackerBottomAppBar(modifier: Modifier = Modifier) {
    // State to keep track of the currently selected item index.
    // Since there is no navigation graph yet, we manage this state locally.
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    // NavigationBar is the Material 3 component for bottom navigation.
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 3.dp
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            val isSelected = selectedItemIndex == index

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    // Update the selected item state on click.
                    selectedItemIndex = index
                    // TODO: Trigger navigation here once the navigation graph is set up.
                },
                icon = {
                    // Use Crossfade to smoothly animate between selected and unselected icons.
                    Crossfade(
                        targetState = isSelected,
                        label = "${item.label} Icon Animation"
                    ) { selected ->
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                // Ensures labels are always visible as per the design in the image.
                alwaysShowLabel = true
            )
        }
    }
}