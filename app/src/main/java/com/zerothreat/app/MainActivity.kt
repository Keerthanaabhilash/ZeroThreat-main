package com.zerothreat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation. layout.padding
import androidx.compose.material. icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui. Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.*
import com.zerothreat.app.ui.alerts.ThreatAlertDialog
import com.zerothreat.app.ui.alerts.ThreatLevel
import com.zerothreat.app.ui.dashboard.DashboardScreen
import com.zerothreat.app.ui.manual.ManualCheckScreen
import com.zerothreat.app.ui.mode.AppMode
import com.zerothreat. app.ui.mode.ModeSelectionScreen
import com.zerothreat.app.ui.onboarding.OnboardingScreen
import com.zerothreat.app.ui.permissions.PermissionRequestScreen
import com. zerothreat.app.ui.settings.SettingsScreen
import com.zerothreat. app.ui.splash.SplashScreen
import com.zerothreat.app.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroThreatTheme {
                ZeroThreatApp()
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Permissions : Screen("permissions")
    object ModeSelection : Screen("mode")
    object Dashboard : Screen("dashboard")
    object ManualCheck : Screen("manual")
    object Settings : Screen("settings")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZeroThreatApp() {
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(false) }

    val navBackStackEntry by navController. currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        showBottomBar = currentRoute in listOf(
            Screen.Dashboard.route,
            Screen.ManualCheck.route
        )
    }

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = DarkBackground
                ) {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Dashboard.route,
                        onClick = {
                            navController.navigate(Screen.Dashboard.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                        label = { Text("Dashboard") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ElectricPurple,
                            selectedTextColor = ElectricPurple,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted,
                            indicatorColor = ElectricPurple. copy(alpha = 0.2f)
                        )
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.ManualCheck. route,
                        onClick = {
                            navController.navigate(Screen.ManualCheck.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Default. Search, contentDescription = "Check Link") },
                        label = { Text("Check Link") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ElectricPurple,
                            selectedTextColor = ElectricPurple,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted,
                            indicatorColor = ElectricPurple.copy(alpha = 0.2f)
                        )
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.Settings.route,
                        onClick = {
                            navController.navigate(Screen.Settings. route) {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ElectricPurple,
                            selectedTextColor = ElectricPurple,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted,
                            indicatorColor = ElectricPurple.copy(alpha = 0.2f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier. padding(paddingValues)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onSplashFinished = {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Splash. route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onFinish = {
                        navController.navigate(Screen.Permissions. route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Permissions. route) {
                PermissionRequestScreen(
                    onPermissionsGranted = {
                        navController.navigate(Screen.ModeSelection.route) {
                            popUpTo(Screen. Permissions.route) { inclusive = true }
                        }
                    },
                    onSkip = {
                        navController. navigate(Screen.ModeSelection.route) {
                            popUpTo(Screen. Permissions.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.ModeSelection.route) {
                ModeSelectionScreen(
                    onModeSelected = { mode ->
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.ModeSelection.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Dashboard.route) {
                DashboardScreen()
            }

            composable(Screen.ManualCheck.route) {
                var showAlert by remember { mutableStateOf(false) }
                var checkedUrl by remember { mutableStateOf("") }

                ManualCheckScreen(
                    onCheckUrl = { url ->
                        checkedUrl = url
                        showAlert = true
                    }
                )

                if (showAlert) {
                    ThreatAlertDialog(
                        url = checkedUrl,
                        threatLevel = ThreatLevel. PHISHING, // Demo - replace with actual detection
                        reason = "This URL matches known phishing patterns",
                        onBlock = { showAlert = false },
                        onViewSafely = { showAlert = false },
                        onIgnore = { showAlert = false },
                        onReport = { showAlert = false },
                        onDismiss = { showAlert = false }
                    )
                }
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}