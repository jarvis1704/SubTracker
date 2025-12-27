package com.biprangshu.subtracker.ui.screens.addsubscriptionscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddSubscriptionScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    Surface(
        modifier= Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(
                top = innerPadding.calculateTopPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
        ) {

            Text(
                text = "Add Your Subscription",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}