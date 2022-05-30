package com.free.core.exceptions

sealed class GithubApiException(message: String, statusCode: Int) : Exception(message) {
    class NotModifiedException(message: String, statusCode: Int) :
        GithubApiException(message, statusCode)

    class UnauthenticatedException(message: String, statusCode: Int) :
        GithubApiException(message, statusCode)

    class ForbiddenException(message: String, statusCode: Int) :
        GithubApiException(message, statusCode)

    class UnknownException(message: String, statusCode: Int) :
        GithubApiException(message, statusCode)

    companion object {
        fun fromStatusCode(statusCode: Int): GithubApiException {
            return when (statusCode) {
                304 -> NotModifiedException("not modified exception", statusCode)
                401 -> UnauthenticatedException("unauthenticated exception", statusCode)
                403 -> ForbiddenException("forbidden exception", statusCode)
                else -> UnknownException("unknown exception", statusCode)
            }
        }
    }
}