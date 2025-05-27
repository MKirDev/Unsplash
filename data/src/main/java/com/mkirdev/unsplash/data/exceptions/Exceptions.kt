package com.mkirdev.unsplash.data.exceptions

sealed class OnboardingException : Throwable() {
    data class SaveFlagException(
        override val cause: Throwable
    ) : OnboardingException()

    data class GetFlagException(
        override val cause: Throwable
    ) : OnboardingException()

    data class ClearDataException(
        override val cause: Throwable
    ) : OnboardingException()
}

sealed class AuthException : Throwable() {
    data class GetAuthRequestException(
        override val cause: Throwable
    ) : AuthException()

    data class PerformTokenRequestException(
        override val cause: Throwable
    ) : AuthException()

    data class GetSavedTokenRequestException(
        override val cause: Throwable
    ) : AuthException()

    data class ClearDataException(
        override val cause: Throwable
    ) : AuthException()
}