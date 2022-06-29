package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.free.domain.entities.User
import com.free.domain.usecases.FetchCurrentUserUseCase
import com.free.domain.usecases.ResetPasswordUseCase
import com.free.domain.usecases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class SettingsUiState(
    val isLoading: Boolean = false,
    val exception: Exception? = null,
    val currentUser: User? = null,
    val isShowingSignOutDialog: Boolean = false
)


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val fetchCurrentUserUseCase: FetchCurrentUserUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState get() = _uiState.asStateFlow()

    suspend fun fetchCurrentUser() {
        _uiState.value = uiState.value.copy(isLoading = true)
        val user = fetchCurrentUserUseCase.execute()
        _uiState.value = uiState.value.copy(currentUser = user, isLoading = false)
    }

    fun onShowSignOutDialog() {
        _uiState.value = uiState.value.copy(isShowingSignOutDialog = true)
    }

    fun onSignOut() {
        signOutUseCase.execute()
        _uiState.value = uiState.value.copy(
            isShowingSignOutDialog = false,
            currentUser = null
        )
    }

    fun onCloseDialog() {
        _uiState.value = uiState.value.copy(isShowingSignOutDialog = false)
    }
}