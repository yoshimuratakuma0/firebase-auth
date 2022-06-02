package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.free.domain.entities.User
import com.free.domain.usecases.FetchCurrentUserUseCase
import com.free.domain.usecases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val exception: Exception? = null,
    val currentUser: User? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCurrentUserUseCase: FetchCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    suspend fun currentUser(): User? = fetchCurrentUserUseCase.execute()

    fun onSignOut() {
        signOutUseCase.execute()
    }
}