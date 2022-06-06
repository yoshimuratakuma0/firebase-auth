package com.free.data.exceptions

import com.free.core.exceptions.AuthenticationException
import com.free.core.exceptions.AuthenticationException.*
import com.google.firebase.auth.FirebaseAuthException

fun FirebaseAuthException.toCoreException(): AuthenticationException {
    return when (this.errorCode) {
        "ERROR_INVALID_EMAIL" -> InvalidEmailException()
        "ERROR_WEAK_PASSWORD" -> WeakPasswordException()
        "ERROR_EMAIL_ALREADY_IN_USE" -> EmailAlreadyInUseException()
        "ERROR_USER_DISABLED" -> UserDisabledException()
        "ERROR_WRONG_PASSWORD" -> WrongPasswordException()
        "ERROR_USER_NOT_FOUND" -> UserNotFoundException()
        else -> UndefinedException()
    }
}