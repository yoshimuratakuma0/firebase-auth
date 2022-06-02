package com.free.domain.usecases

import com.free.core.Result
import com.free.domain.entities.User
import com.free.domain.repositories.AuthRepository
import com.free.domain.repositories.AuthRepositoryAnnotation
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    @AuthRepositoryAnnotation
    private val repository: AuthRepository
) {
    suspend fun execute(params: SignInInputParams): Result<User> {
        return repository.signIn(params)
    }
}

class SignInInputParams(
    val email: String,
    val password: String
)