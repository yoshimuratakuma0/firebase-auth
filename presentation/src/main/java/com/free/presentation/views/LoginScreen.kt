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
import com.free.domain.usecases.SignInInputParams
import com.free.domain.usecases.SignUpInputParams
import com.free.presentation.R
import com.free.presentation.viewmodels.LoginViewModel
import com.free.presentation.viewmodels.ScreenType

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_login))
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val uiState = viewModel.uiState.collectAsState()
                if (uiState.value.hasLogin) {
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                }

                Text(text = stringResource(id = uiState.value.screenType.titleRes))
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
                        when (uiState.value.screenType) {
                            is ScreenType.SignIn -> {
                                viewModel.onSignIn(
                                    SignInInputParams(
                                        uiState.value.username,
                                        uiState.value.password
                                    )
                                )
                            }
                            is ScreenType.SignUp -> {
                                viewModel.onSignUp(
                                    SignUpInputParams(
                                        uiState.value.username,
                                        uiState.value.password
                                    )
                                )
                            }
                        }
                    }
                ) {
                    Text(stringResource(id = uiState.value.screenType.titleRes))
                }

                TextButton(onClick = {
                    viewModel.onChangeScreenType()
                }) {
                    Text(stringResource(id = uiState.value.changeButtonType.titleRes))
                }
            }
        }
    )
}

