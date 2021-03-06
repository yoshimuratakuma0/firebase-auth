package com.free.data.repositories

import com.free.core.Result
import com.free.core.exceptions.AuthenticationException.EmailHasNotConfirmed
import com.free.data.exceptions.coreException
import com.free.data.models.entity
import com.free.domain.entities.User
import com.free.domain.repositories.AuthRepository
import com.free.domain.usecases.ResetPasswordInputParams
import com.free.domain.usecases.SignInInputParams
import com.free.domain.usecases.SignUpInputParams
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
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
                                        it.resume(Result.Error(exception))
                                    }
                                }
                            }
                        } ?: run {
                            it.resume(Result.Error(Exception()))
                        }
                    } else {
                        task.exception?.let { exception ->
                            val convertedException = when (exception) {
                                is FirebaseAuthException -> exception.coreException
                                is FirebaseNetworkException -> exception.coreException
                                else -> exception
                            }
                            it.resume(Result.Error(convertedException))
                        }
                    }
                }
        }
    }

    override suspend fun signIn(params: SignInInputParams): Result<User> {
        return suspendCoroutine {
            try {
                auth.signInWithEmailAndPassword(params.email, params.password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            auth.currentUser?.let { user ->
                                if (user.isEmailVerified) {
                                    it.resume(Result.Success(user.entity))
                                } else {
                                    auth.signOut()
                                    it.resume(Result.Error(EmailHasNotConfirmed()))
                                }
                            }
                        } else {
                            task.exception?.let { exception ->
                                val convertedException = when (exception) {
                                    is FirebaseAuthException -> exception.coreException
                                    is FirebaseNetworkException -> exception.coreException
                                    else -> exception
                                }
                                it.resume(Result.Error(convertedException))
                            }
                        }
                    }
            } catch (e: Exception) {
                it.resume(Result.Error(e))
            }
        }
    }

    override fun signOut() {
        auth.signOut()
    }


    override suspend fun resetPassword(params: ResetPasswordInputParams): Result<Unit> {
        return suspendCoroutine {
            auth.currentUser?.updatePassword(params.password)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.resume(Result.Success(data = Unit))
                } else {
                    task.exception?.let { exception ->
                        it.resume(Result.Error(exception))
                    }
                }
            }
        }
    }
}