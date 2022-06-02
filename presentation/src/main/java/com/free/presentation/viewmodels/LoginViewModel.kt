package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.free.core.Result
import com.free.domain.entities.User
import com.free.domain.usecases.SignInInputParams
import com.free.domain.usecases.SignInUseCase
import com.free.domain.usecases.SignUpInputParams
import com.free.domain.usecases.SignUpUseCase
import com.free.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ScreenType(val titleRes: Int) {
    object SignIn : ScreenType(R.string.sign_in)
    object SignUp : ScreenType(R.string.sign_up)
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val hasLogin: Boolean = false,
    val exception: Exception? = null,
    val username: String = "",
    val password: String = "",
    val currentUser: User? = null,
    val screenType: ScreenType = ScreenType.SignUp
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState(isLoading = false))
    val uiState get() = _uiState.asStateFlow()

    fun setUsername(username: String) {
        _uiState.value = uiState.value.copy(username = username)
    }

    fun setPassword(password: String) {
        _uiState.value = uiState.value.copy(password = password)
    }

    fun onSignUp(inputParams: SignUpInputParams) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            signUpUseCase.execute(inputParams).let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = uiState.value.copy(
                            currentUser = result.data,
                            screenType = ScreenType.SignIn,
                            isLoading = false
                        )
                    }
                    is Result.Error -> {
                        _uiState.value = uiState.value.copy(
                            exception = result.exception,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onSignIn(inputParams: SignInInputParams) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            signInUseCase.execute(inputParams).let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = uiState.value.copy(
                            currentUser = result.data,
                            isLoading = false,
                            hasLogin = true
                        )
                    }
                    is Result.Error -> {
                        _uiState.value = uiState.value.copy(
                            exception = result.exception,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}