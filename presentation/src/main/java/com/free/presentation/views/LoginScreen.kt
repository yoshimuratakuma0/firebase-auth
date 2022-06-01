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
import com.free.domain.usecases.SignUpInputParams
import com.free.presentation.R
import com.free.presentation.viewmodels.LoginViewModel
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

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
                val username = viewModel.username.collectAsState()
                OutlinedTextField(
                    value = username.value,
                    onValueChange = viewModel::setUsername,
                    singleLine = true
                )

                val password = viewModel.password.collectAsState()
                OutlinedTextField(
                    value = password.value,
                    onValueChange = viewModel::setPassword,
                    singleLine = true
                )

                val uiState = viewModel.uiState.collectAsState()
                Button(
                    onClick = {
                        viewModel.onSignUp(SignUpInputParams(username.value, password.value))
                    }
                ) {
                    if (uiState.value.exception is FirebaseAuthInvalidCredentialsException) {
                        val a = (uiState.value.exception as FirebaseAuthInvalidCredentialsException).message
                    }
                }
            }
        }
    )
}

