package com.free.presentation.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.free.presentation.viewmodels.HomeViewModel
import com.free.presentation.viewmodels.LoginViewModel
import com.free.presentation.views.theme.AuthTheme
import dagger.hilt.android.AndroidEntryPoint

object ScreenRoutes {
    const val login = "login/"
    const val home = "home/"
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AuthTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoutes.home
                ) {
                    composable(route = ScreenRoutes.home) {
                        val viewModel: HomeViewModel = hiltViewModel()
                        HomeScreen(viewModel, navController)
                    }
                    composable(route = ScreenRoutes.login) {
                        val viewModel: LoginViewModel = hiltViewModel()
                        LoginScreen(viewModel, navController)
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