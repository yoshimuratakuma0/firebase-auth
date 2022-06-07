package com.free.data.exceptions

import com.free.core.exceptions.AuthenticationException
import com.free.core.exceptions.AuthenticationException.*
import com.free.core.exceptions.NetworkException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException

val FirebaseAuthException.coreException
    get() : AuthenticationException =
        when (this.errorCode) {
            "ERROR_INVALID_EMAIL" -> InvalidEmailException()
            "ERROR_WEAK_PASSWORD" -> WeakPasswordException()
            "ERROR_EMAIL_ALREADY_IN_USE" -> EmailAlreadyInUseException()
            "ERROR_USER_DISABLED" -> UserDisabledException()
            "ERROR_WRONG_PASSWORD" -> WrongPasswordException()
            "ERROR_USER_NOT_FOUND" -> UserNotFoundException()
            else -> UndefinedException()
        }

val FirebaseNetworkException.coreException
    get(): NetworkException = NetworkException()