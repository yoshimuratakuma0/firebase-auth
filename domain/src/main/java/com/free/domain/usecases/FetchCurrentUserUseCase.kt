package com.free.domain.usecases

import com.free.domain.entities.User
import com.free.domain.repositories.AuthRepository
import com.free.domain.repositories.AuthRepositoryAnnotation
import javax.inject.Inject

class FetchCurrentUserUseCase @Inject constructor(
    @AuthRepositoryAnnotation
    private val repository: AuthRepository
) {
    suspend fun execute(): User? {
        return repository.fetchCurrentUser()
    }
}