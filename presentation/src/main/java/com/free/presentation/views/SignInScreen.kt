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
import com.free.presentation.R
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
        }
    )
}