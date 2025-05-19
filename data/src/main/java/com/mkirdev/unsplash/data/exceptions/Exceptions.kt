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