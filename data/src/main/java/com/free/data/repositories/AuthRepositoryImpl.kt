package com.free.data.repositories

import com.free.core.Result
import com.free.domain.entities.User
import com.free.domain.repositories.AuthRepository
import com.free.domain.usecases.SignUpInputParams
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class FirebaseAuthRepositoryImpl : AuthRepository {
    private var auth: FirebaseAuth = Firebase.auth

    override suspend fun fetchCurrentUser() {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(params: SignUpInputParams): Result<User> {
        return suspendCoroutine {
            auth.createUserWithEmailAndPassword(params.email, params.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.let { user ->
                            it.resume(
                                Result.Success(
                                    User(
                                        name = user.displayName ?: "",
                                        email = user.email ?: "",
                                        uid = user.uid
                                    )
                                )
                            )
                        } ?: run {
                            it.resume(Result.Error(Exception()))
                        }
                    } else {
                        task.exception?.let { exception ->
                            it.resume(Result.Error(exception))
                        }
                    }
                }
        }
    }

    override suspend fun signIn() {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}