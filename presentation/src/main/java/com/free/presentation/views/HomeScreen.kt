package com.free.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.free.presentation.R
import com.free.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by produceState(
        initialValue = viewModel.uiState.collectAsState().value.copy(isLoading = true)
    ) {
        viewModel.fetchCurrentUser()
        value = viewModel.uiState.value
    }

    if (!uiState.isLoading && uiState.currentUser == null) {
        LaunchedEffect(Unit) {
            navController.navigate(ScreenRoutes.signUp)
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
                Text(text = uiState.currentUser?.uid ?: "")
            }
        }
    )
}