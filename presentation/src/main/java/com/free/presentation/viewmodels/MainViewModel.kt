package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.free.domain.entities.User
import com.free.domain.usecases.FetchCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class MainUiState(
    val currentUser: User? = null,
    val isLoading: Boolean = false,
    val exception: Exception? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchCurrentUserUseCase: FetchCurrentUserUseCase
) : ViewModel() {

    suspend fun currentUser(): User? = fetchCurrentUserUseCase.execute()
}