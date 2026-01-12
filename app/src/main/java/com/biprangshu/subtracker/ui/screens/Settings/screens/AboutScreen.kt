package com.biprangshu.subtracker.ui.screens.Settings.screens

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biprangshu.subtracker.R // Ensure this R import is correct for your package
import com.biprangshu.subtracker.ui.screens.Settings.components.SettingsItem
import com.biprangshu.subtracker.ui.theme.AppFonts.robotoFlexTopBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    // Helper to open URLs
    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    // Social Links Data
    // Replace Icons with your actual drawables or vectors if available
    val socialLinks = remember {
        listOf(
            SocialLink(R.drawable.github_logo, "https://github.com/jarvis1704"),
            SocialLink(R.drawable.x_logo, url="https://x.com/DasBiprangshu"),
            SocialLink(R.drawable.linkedin_logo, url="https://www.linkedin.com/in/biprangshu-das-34017427a/"),
            SocialLink(R.drawable.gmail_logo, url="mailto:dasbiprangshu@gmail.com")
        )
    }

    var showLicense by rememberSaveable { mutableStateOf(false) }

    // Shapes for the grouped card look
    val topListItemShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    val bottomListItemShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
    val containerColor = MaterialTheme.colorScheme.surfaceContainerHigh

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "About",
                        style = TextStyle(
                            fontFamily = robotoFlexTopBar,
                            fontSize = 32.sp
                        )
                    )
                },
                navigationIcon = {
                    FilledTonalIconButton(
                        onClick = onBack,
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item { Spacer(Modifier.height(8.dp)) }

            // --- App Info Card ---
            item {
                Box(
                    modifier = Modifier
                        .clip(topListItemShape)
                        .background(containerColor)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // App Icon Placeholder
                        Icon(
                            painterResource(R.drawable.ic_launcher_monochrome), // Replace with R.drawable.ic_launcher_foreground
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialShapes.Cookie7Sided.toShape()
                                )
                                .padding(12.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "v1.0.0", // You can use BuildConfig.VERSION_NAME
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(Modifier.weight(1f))

                        // Action Buttons (e.g., GitHub repo)
                        FilledTonalIconButton(
                            onClick = { openUrl("https://github.com/biprangshu/SubTracker") },
                            shapes = IconButtonDefaults.shapes()
                        ) {
                            Icon(
                                painterResource(R.drawable.github_logo), // Replace with GitHub Icon
                                contentDescription = "GitHub",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            // --- Developer Info Card ---
            item {
                Box(
                    modifier = Modifier
                        .clip(bottomListItemShape)
                        .background(containerColor)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Developer PFP Placeholder
                            Icon(
                                imageVector = Icons.Default.Person,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(
                                        MaterialTheme.colorScheme.secondaryContainer,
                                        CircleShape
                                    )
                                    .padding(8.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(
                                    "Biprangshu Das",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "Developer",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))

                        // Social Links
                        Row {
                            Spacer(Modifier.width((64 + 16).dp)) // Indent to align with text
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                socialLinks.forEach { link ->
                                    FilledTonalIconButton(
                                        onClick = { openUrl(link.url) },
                                        shapes = IconButtonDefaults.shapes(),
                                        modifier = Modifier.width(52.dp)
                                    ) {
                                        Icon(
                                            painterResource(link.icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(ButtonDefaults.SmallIconSize)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(24.dp)) }

            // --- License Button ---
            item {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "Open Source License",
                    subtitle = "GNU General Public License v3.0",
                    shape = RoundedCornerShape(24.dp),
                    onClick = { showLicense = true }
                )
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }

    if (showLicense) {
        SimpleLicenseBottomSheet(onDismiss = { showLicense = false })
    }
}

// Data class for social buttons
data class SocialLink(
    val icon: Int,
    val url: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleLicenseBottomSheet(onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = "License",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
            )

            // Shortened License Text for display
            Text(
                text = "Copyright (c) 2025 Biprangshu\n\n" +
                        "This file is part of SubTracker.\n\n" +
                        "SubTracker is free software: you can redistribute it and/or modify it under the terms of the GNU " +
                        "General Public License as published by the Free Software Foundation, either version 3 of the " +
                        "License, or (at your option) any later version.\n\n" +
                        "SubTracker is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even " +
                        "the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General " +
                        "Public License for more details.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}