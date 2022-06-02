package com.free.domain.repositories

import com.free.core.Result
import com.free.domain.entities.User
import com.free.domain.usecases.SignInInputParams
import com.free.domain.usecases.SignUpInputParams

interface AuthRepository {
    suspend fun fetchCurrentUser(): User?
    suspend fun signUp(params: SignUpInputParams): Result<User>
    suspend fun signIn(params: SignInInputParams): Result<User>
    fun signOut()
}