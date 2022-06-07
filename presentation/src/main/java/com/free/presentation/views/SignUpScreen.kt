package com.free.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.free.core.exceptions.AuthenticationException.*
import com.free.domain.usecases.SignUpInputParams
import com.free.presentation.R
import com.free.presentation.utils.LoadingScreen
import com.free.presentation.utils.OkAlertDialog
import com.free.presentation.viewmodels.SignUpViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_sign_up))
                }
            )
        },
        content = {
            val uiState = viewModel.uiState.collectAsState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.title_sign_up))
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
                        viewModel.onSignUp(
                            SignUpInputParams(
                                uiState.value.username,
                                uiState.value.password
                            )
                        )
                    }
                ) {
                    Text(stringResource(id = R.string.title_sign_up))
                }

                TextButton(onClick = {
                    navController.navigate(ScreenRoutes.signIn) {
                        navController.popBackStack()
                    }
                }) {
                    Text(stringResource(id = R.string.change_to_sign_in))
                }
            }
            if (uiState.value.isLoading)
                LoadingScreen()

            if (uiState.value.currentUser != null)
                OkAlertDialog(bodyResId = R.string.sent_email)

            if (!uiState.value.isLoading)
                when (uiState.value.exception) {
                    is WrongPasswordException -> OkAlertDialog(
                        bodyResId = R.string.error_user_not_found_or_wrong_password
                    )
                    is UserNotFoundException -> OkAlertDialog(
                        bodyResId = R.string.error_user_not_found_or_wrong_password
                    )
                    is EmailHasNotConfirmed -> {
                        // emailが登録済みであることは秘匿する
                        OkAlertDialog(bodyResId = R.string.sent_email)
                    }
                }
        }
    )
}

