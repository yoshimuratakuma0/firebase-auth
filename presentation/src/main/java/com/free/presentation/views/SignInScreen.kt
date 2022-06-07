package com.free.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.free.core.exceptions.AuthenticationException.*
import com.free.core.exceptions.NetworkException
import com.free.domain.usecases.SignInInputParams
import com.free.presentation.R
import com.free.presentation.utils.LoadingScreen
import com.free.presentation.utils.OkAlertDialog
import com.free.presentation.viewmodels.SignInViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_sign_in))
                }
            )
        },
        content = {
            val uiState = viewModel.uiState.collectAsState()
            if (uiState.value.hasLogin) {
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(text = stringResource(id = R.string.title_sign_in))
                OutlinedTextField(
                    value = uiState.value.username,
                    onValueChange = viewModel::setUsername,
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.value.password,
                    onValueChange = viewModel::setPassword,
                    singleLine = true
                )

                Button(
                    onClick = {
                        viewModel.onSignIn(
                            SignInInputParams(
                                uiState.value.username,
                                uiState.value.password
                            )
                        )
                    }
                ) {
                    Text(stringResource(id = R.string.title_sign_in))
                }

                TextButton(onClick = {
                    navController.navigate(ScreenRoutes.signUp) {
                        navController.popBackStack()
                    }
                }) {
                    Text(stringResource(id = R.string.change_to_sign_up))
                }
            }

            if (uiState.value.isLoading)
                LoadingScreen()

            if (!uiState.value.isLoading)
                when (uiState.value.exception) {
                    is WrongPasswordException -> OkAlertDialog(
                        bodyResId = R.string.error_user_not_found_or_wrong_password
                    )
                    is UserNotFoundException -> OkAlertDialog(
                        bodyResId = R.string.error_user_not_found_or_wrong_password
                    )
                    is EmailHasNotConfirmed -> {
                        OkAlertDialog(bodyResId = R.string.error_email_has_not_confirmed)
                    }
                    is IllegalArgumentException -> {
                        OkAlertDialog(bodyResId = R.string.error_fill_in_required_items)
                    }
                    is NetworkException -> {
                        OkAlertDialog(bodyResId = R.string.error_network)
                    }
                }
        }
    )
}