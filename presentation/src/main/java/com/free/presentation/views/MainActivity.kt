package com.free.presentation.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.free.presentation.R
import com.free.presentation.viewmodels.HomeViewModel
import com.free.presentation.viewmodels.SettingsViewModel
import com.free.presentation.viewmodels.SignInViewModel
import com.free.presentation.viewmodels.SignUpViewModel
import com.free.presentation.views.theme.AuthTheme
import dagger.hilt.android.AndroidEntryPoint

object ScreenRoutes {
    const val signUp = "sign_up/"
    const val signIn = "sign_in/"
    const val home = "home/"
    const val settings = "settings/"
}


sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Home : Screen(
        ScreenRoutes.home,
        R.string.title_home,
        Icons.Default.Home
    )

    object Settings : Screen(
        ScreenRoutes.settings,
        R.string.title_settings,
        Icons.Default.Settings
    )
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AuthTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        val items = listOf(
                            Screen.Home,
                            Screen.Settings,
                        )
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            screen.icon,
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(id = screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.route == screen.route
                                    } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoutes.home,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(route = ScreenRoutes.signUp) {
                            val viewModel: SignUpViewModel = hiltViewModel()
                            SignUpScreen(viewModel, navController)
                        }
                        composable(route = ScreenRoutes.signIn) {
                            val viewModel: SignInViewModel = hiltViewModel()
                            SignInScreen(viewModel, navController)
                        }
                        composable(route = ScreenRoutes.home) {
                            val viewModel: HomeViewModel = hiltViewModel()
                            HomeScreen(viewModel, navController)
                        }
                        composable(route = ScreenRoutes.settings) {
                            val viewModel: SettingsViewModel = hiltViewModel()
                            SettingsScreen(viewModel, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AuthTheme {
        Greeting("Android")
    }
}