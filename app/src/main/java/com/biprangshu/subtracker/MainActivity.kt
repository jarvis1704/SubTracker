package com.biprangshu.subtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.biprangshu.subtracker.ui.components.Fab
import com.biprangshu.subtracker.ui.components.SubTrackerBottomAppBar
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen
import com.biprangshu.subtracker.ui.screens.HomeScreen
import com.biprangshu.subtracker.ui.theme.SubTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SubTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        SubTrackerBottomAppBar()
                    },
                    floatingActionButton = {
                        Fab() {
                            //onclick redirection
                        }
                    }

                ) { innerPadding ->
//                    HomeScreen(
//                        innerPadding = innerPadding
//                    )
                    AnalyticsScreen(
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

