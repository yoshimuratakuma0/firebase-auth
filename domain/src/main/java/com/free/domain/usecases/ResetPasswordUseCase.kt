package com.free.domain.usecases

import com.free.domain.repositories.AuthRepository
import com.free.domain.repositories.AuthRepositoryAnnotation
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    @AuthRepositoryAnnotation
    private val repository: AuthRepository
) {
    suspend fun execute(params: ResetPasswordInputParams) {
        repository.resetPassword(params)
    }
}

class ResetPasswordInputParams(
    val password: String
)