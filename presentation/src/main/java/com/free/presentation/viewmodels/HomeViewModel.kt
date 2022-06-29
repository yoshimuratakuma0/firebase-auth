package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.free.domain.entities.User
import com.free.domain.usecases.FetchCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val exception: Exception? = null,
    val currentUser: User? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCurrentUserUseCase: FetchCurrentUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun fetchCurrentUser() {
        val user = fetchCurrentUserUseCase.execute()
        _uiState.value = uiState.value.copy(currentUser = user, isLoading = false)
    }
}