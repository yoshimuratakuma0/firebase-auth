package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.free.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    signUpUseCase: SignUpUseCase
) : ViewModel() {

    fun onSignUp() {

    }
}