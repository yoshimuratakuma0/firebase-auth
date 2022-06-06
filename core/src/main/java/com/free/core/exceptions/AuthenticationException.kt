package com.free.core.exceptions

sealed class AuthenticationException : Exception() {
    // exceptions for sign up
    class InvalidEmailException : AuthenticationException()
    class EmailAlreadyInUseException : AuthenticationException()
    class WeakPasswordException : AuthenticationException()

    // exceptions for sign in
    class WrongPasswordException : AuthenticationException()
    class UserNotFoundException : AuthenticationException()
    class UserDisabledException : AuthenticationException()

    class UndefinedException : AuthenticationException()
}