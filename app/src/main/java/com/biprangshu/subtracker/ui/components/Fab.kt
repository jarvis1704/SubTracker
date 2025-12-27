package com.biprangshu.subtracker.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.biprangshu.subtracker.navigation.Route

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Fab(
    modifier: Modifier = Modifier,
    onClick: (Route) -> Unit
) {
    MediumFloatingActionButton(
        onClick = { onClick(Route.AddSubscriptionScreen) },
        modifier = modifier,
        // Material 3 Default FAB shape is a rounded rectangle (usually 16.dp),
        // but you can customize it here if you want it more "expressive".
        shape = FloatingActionButtonDefaults.shape,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Subscription",
            modifier = Modifier.size(32.dp)
        )
    }
}