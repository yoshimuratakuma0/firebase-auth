package com.free.domain.repositories

import com.free.core.Result
import com.free.domain.entities.User
import com.free.domain.usecases.SignUpInputParams

interface AuthRepository {
    suspend fun fetchCurrentUser()
    suspend fun signUp(params: SignUpInputParams): Result<User>
    suspend fun signIn()
    suspend fun signOut()
}