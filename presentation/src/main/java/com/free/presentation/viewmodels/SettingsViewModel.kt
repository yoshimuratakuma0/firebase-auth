package com.free.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.free.domain.usecases.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

}