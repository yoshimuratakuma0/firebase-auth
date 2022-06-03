package com.free.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.free.presentation.R
import com.free.presentation.viewmodels.HomeUiState
import com.free.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by produceState(
        initialValue = HomeUiState(isLoading = true)
    ) {
        val currentUser = viewModel.currentUser()
        value = HomeUiState(isLoading = false, currentUser = currentUser)
    }

    if (!uiState.isLoading && uiState.currentUser == null) {
        LaunchedEffect(Unit) {
            navController.navigate(ScreenRoutes.login)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_home))
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = "Home")

                TextButton(onClick = {
                    navController.navigate(ScreenRoutes.login) {
                        viewModel.onSignOut()
                        navController.navigate(ScreenRoutes.login)
                    }
                }) {
                    Text(text = stringResource(id = R.string.sign_out))
                }
            }
        }
    )
}