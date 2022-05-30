package com.free.domain.usecases

import com.free.domain.repositories.AuthRepository
import com.free.domain.repositories.AuthRepositoryAnnotation
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    @AuthRepositoryAnnotation
    private val repository: AuthRepository
) {
    suspend fun execute() {
        repository.signOut()
    }
}