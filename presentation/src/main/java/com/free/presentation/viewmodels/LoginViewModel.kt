package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.free.core.Result
import com.free.domain.usecases.SignUpInputParams
import com.free.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val exception: Exception? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState(isLoading = false))
    val uiState get() = _uiState.asStateFlow()

    private val _username = MutableStateFlow("")
    val username get() = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password get() = _password.asStateFlow()

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun onSignUp(inputParams: SignUpInputParams) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            signUpUseCase.execute(inputParams).let { result ->
                when (result) {
                    is Result.Success -> {
                        val a = result.data
                        val b = 2
                    }
                    is Result.Error -> {
                        _uiState.value = uiState.value.copy(exception = result.exception)
                    }
                }
            }

            _uiState.value = uiState.value.copy(isLoading = false)
        }
    }
}