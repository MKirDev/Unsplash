package com.mkirdev.unsplash.data.exceptions

internal sealed class OnboardingException : Throwable() {
    internal data class SaveFlagException(
        override val cause: Throwable?
    ) : OnboardingException()

    internal data class GetFlagException(
        override val cause: Throwable?
    ) : OnboardingException()

    internal data class ClearDataException(
        override val cause: Throwable?
    ) : OnboardingException()
}

internal sealed class AuthException : Throwable() {
    internal data class GetAuthRequestException(
        override val cause: Throwable?
    ) : AuthException()

    internal data class PerformTokenRequestException(
        override val cause: Throwable?
    ) : AuthException()

    internal data class GetSavedTokenRequestException(
        override val cause: Throwable?
    ) : AuthException()

    internal data class ClearDataException(
        override val cause: Throwable?
    ) : AuthException()
}

internal sealed class PhotosException : Throwable() {
    internal data class GetPhotosException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class GetPhotoException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class LikePhotoLocalException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class UnlikePhotoLocalException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class LikePhotoRemoteException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class UnlikePhotoRemoteException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class GetLikedPhotoException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class GetUnlikedPhotoException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class AddDownloadLinkException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class GetDownloadLinkException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class ClearDataException(
        override val cause: Throwable?
    ) : PhotosException()

    internal data class ClearPhotosFromDatabase(
        override val cause: Throwable?
    ) : PhotosException()
}

internal sealed class SearchException : Throwable() {
    internal data class SearchPhotosException(
        override val cause: Throwable?
    ) : SearchException()
}

internal sealed class CollectionsException : Throwable() {

    internal data class GetCollectionsException(
        override val cause: Throwable?
    ) : CollectionsException()

    internal data class GetCollectionInfoException(
        override val cause: Throwable?
    ) : CollectionsException()

    internal data class GetCollectionPhotosException(
        override val cause: Throwable?
    ) : CollectionsException()

    internal data class ClearCollectionsFromDatabase(
        override val cause: Throwable?
    ) : CollectionsException()

}

internal sealed class PreferencesException : Throwable() {

    internal data class SaveScheduleFlagException(
        override val cause: Throwable?
    ) : PreferencesException()

    internal data class GetScheduleFlagException(
        override val cause: Throwable?
    ) : PreferencesException()

    internal data class DeleteScheduleFlagException(
        override val cause: Throwable?
    ) : PreferencesException()
}

internal sealed class UserException : Throwable() {
    internal data class GetLikedPhotosException(
        override val cause: Throwable?
    ) : UserException()

    internal data class GetCurrentUserException(
        override val cause: Throwable?
    ) : UserException()

    internal data class AddCurrentUserException(
        override val cause: Throwable?
    ) : UserException()

    internal data class ClearUserFromDatabase(
        override val cause: Throwable?
    ) : UserException()
}