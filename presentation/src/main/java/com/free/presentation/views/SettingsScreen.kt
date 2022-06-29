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
import com.free.presentation.utils.ConfirmDialog
import com.free.presentation.viewmodels.SettingsViewModel
import com.free.presentation.views.items.ArrowItem
import com.free.presentation.views.items.ArrowItemState

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
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

    if (viewModel.uiState.collectAsState().value.isShowingSignOutDialog) {
        ConfirmDialog(
            bodyResId = R.string.settings_confirm_sign_out,
            onConfirmed = {
                viewModel.onSignOut()
            },
            onCanceled = {
                viewModel.onCloseDialog()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_settings))
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                ArrowItem(
                    ArrowItemState(stringResource(id = R.string.sign_out))
                ) {
                    viewModel.onShowSignOutDialog()
                }
                ArrowItem(
                    ArrowItemState(stringResource(id = R.string.reset_password))
                ) {}
            }
        }
    )
}