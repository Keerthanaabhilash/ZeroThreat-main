package com.zerothreat.app

import android.os.Bundle
import androidx. activity.ComponentActivity
import androidx. activity.compose.setContent
import androidx.compose.foundation.layout. fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui. Modifier
import com.zerothreat.app.ui.dashboard.DashboardScreen
import com.zerothreat.app. ui.theme.ZeroThreatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroThreatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    DashboardScreen()
                }
            }
        }
    }
}