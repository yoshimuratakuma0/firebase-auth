package com.free.data.repositories

import com.free.core.Result
import com.free.core.exceptions.AuthenticationException.*
import com.free.data.models.entity
import com.free.domain.entities.User
import com.free.domain.repositories.AuthRepository
import com.free.domain.usecases.SignInInputParams
import com.free.domain.usecases.SignUpInputParams
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class FirebaseAuthRepositoryImpl : AuthRepository {
    private var auth: FirebaseAuth = Firebase.auth

    override suspend fun fetchCurrentUser(): User? {
        return auth.currentUser?.entity
    }

    override suspend fun signUp(params: SignUpInputParams): Result<User> {
        if (params.email.isEmpty() || params.password.isEmpty()) {
            return Result.Error(Exception())
        }
        return suspendCoroutine {
            auth.createUserWithEmailAndPassword(params.email, params.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.let { user ->
                            user.sendEmailVerification().addOnCompleteListener { emailTask ->
                                if (emailTask.isSuccessful) {
                                    auth.signOut()
                                    it.resume(Result.Success(user.entity))
                                } else {
                                    emailTask.exception?.let { exception ->
                                        val convertedException = when (exception) {
                                            is FirebaseAuthEmailException -> AuthEmailException()
                                            else -> UndefinedException()
                                        }
                                        it.resume(Result.Error(convertedException))
                                    }
                                }
                            }
                        } ?: run {
                            it.resume(Result.Error(Exception()))
                        }
                    } else {
                        task.exception?.let { exception ->
                            val convertedException = when (exception) {
                                is FirebaseAuthWeakPasswordException -> WeakPasswordException()
                                is FirebaseAuthUserCollisionException -> EmailAlreadyInUseException()
                                is FirebaseAuthInvalidCredentialsException -> InvalidEmailException()
                                else -> UndefinedException()
                            }
                            it.resume(Result.Error(convertedException))
                        }
                    }
                }
        }
    }

    override suspend fun signIn(params: SignInInputParams): Result<User> {
        if (params.email.isEmpty() || params.password.isEmpty()) {
            return Result.Error(Exception())
        }
        return suspendCoroutine {
            auth.signInWithEmailAndPassword(params.email, params.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.let { user ->
                            if (user.isEmailVerified) {
                                it.resume(Result.Success(user.entity))
                            } else {
                                auth.signOut()
                                it.resume(Result.Error(Exception()))
                            }
                        }
                    } else {
                        task.exception?.let { exception ->
                            it.resume(Result.Error(exception))
                        }
                    }
                }
        }
    }

    override fun signOut() {
        auth.signOut()
    }
}