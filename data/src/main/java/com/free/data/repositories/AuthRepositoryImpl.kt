package com.free.data.repositories

import com.free.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthRepositoryImpl: AuthRepository {
    private var auth: FirebaseAuth = Firebase.auth
    override suspend fun fetchCurrentUser() {
        TODO("Not yet implemented")
    }

    override suspend fun signIn() {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}