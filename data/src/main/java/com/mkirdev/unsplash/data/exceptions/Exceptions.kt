package com.mkirdev.unsplash.data.exceptions

sealed class OnboardingException : Throwable() {
    data class SaveFlagException(
        override val cause: Throwable?
    ) : OnboardingException()

    data class GetFlagException(
        override val cause: Throwable?
    ) : OnboardingException()

    data class ClearDataException(
        override val cause: Throwable?
    ) : OnboardingException()
}

sealed class AuthException : Throwable() {
    data class GetAuthRequestException(
        override val cause: Throwable?
    ) : AuthException()

    data class PerformTokenRequestException(
        override val cause: Throwable?
    ) : AuthException()

    data class GetSavedTokenRequestException(
        override val cause: Throwable?
    ) : AuthException()

    data class ClearDataException(
        override val cause: Throwable?
    ) : AuthException()
}

sealed class PhotosException : Throwable() {
    data class GetPhotosException(
        override val cause: Throwable?
    ) : PhotosException()

    data class GetPhotoException(
        override val cause: Throwable?
    ) : PhotosException()

    data class LikePhotoLocalException(
        override val cause: Throwable?
    ) : PhotosException()

    data class UnlikePhotoLocalException(
        override val cause: Throwable?
    ) : PhotosException()

    data class LikePhotoRemoteException(
        override val cause: Throwable?
    ) : PhotosException()

    data class UnlikePhotoRemoteException(
        override val cause: Throwable?
    ) : PhotosException()

    data class GetLikedPhotoException(
        override val cause: Throwable?
    ) : PhotosException()

    data class GetUnlikedPhotoException(
        override val cause: Throwable?
    ) : PhotosException()

    data class SearchPhotosException(
        override val cause: Throwable?
    ) : PhotosException()

    data class AddDownloadLinkException(
        override val cause: Throwable?
    ) : PhotosException()

    data class GetDownloadLinkException(
        override val cause: Throwable?
    ) : PhotosException()

    data class ClearDataException(
        override val cause: Throwable?
    ) : PhotosException()
}