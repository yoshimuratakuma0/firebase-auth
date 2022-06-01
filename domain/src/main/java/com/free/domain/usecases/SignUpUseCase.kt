package com.free.domain.usecases

import com.free.core.Result
import com.free.domain.entities.User
import com.free.domain.repositories.AuthRepository
import com.free.domain.repositories.AuthRepositoryAnnotation
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    @AuthRepositoryAnnotation
    private val repository: AuthRepository
) {
    suspend fun execute(params: SignUpInputParams): Result<User> {
        return repository.signUp(params)
    }
}

class SignUpInputParams(
    val email: String,
    val password: String
)