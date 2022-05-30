package com.free.domain.repositories

interface AuthRepository {
    suspend fun fetchCurrentUser()
    suspend fun signIn()
    suspend fun signOut()
}