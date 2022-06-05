package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.free.core.Result
import com.free.domain.entities.User
import com.free.domain.usecases.SignUpInputParams
import com.free.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SignUpUiState(
    val isLoading: Boolean = false,
    val exception: Exception? = null,
    val username: String = "",
    val password: String = "",
    val currentUser: User? = null
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState(isLoading = false))
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
}