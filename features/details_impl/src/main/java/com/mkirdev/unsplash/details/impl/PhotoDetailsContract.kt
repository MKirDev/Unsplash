package com.mkirdev.unsplash.details.impl

import androidx.compose.runtime.Immutable
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.models.PhotoDetailsModel

interface PhotoDetailsContract : UniFlowViewModel<
        PhotoDetailsContract.Event,
        PhotoDetailsContract.State,
        PhotoDetailsContract.Effect?> {

    sealed interface State {

        @Immutable
        data class Success(
            val photoDetailsModel: PhotoDetailsModel,
        ) : State

        @Immutable
        data class Failure(
            val error: String,
            val photoDetailsModel: PhotoDetailsModel?,
            val updatedCount: Int
        ) : State

        @Immutable
        data class DownloadSuccess(
            val photoDetailsModel: PhotoDetailsModel
        ) : State

        @Immutable
        data class DownloadFailure(
            val photoDetailsModel: PhotoDetailsModel
        ) : State

        data object Idle : State

    }

    sealed interface Event {
        data class ShareEvent(val link: String) : Event

        data class DownloadEvent(val link: String) : Event

        data class PhotoLikeEvent(val photoId: String) : Event

        data class PhotoRemoveLikeEvent(val photoId: String) : Event

        data class LocationEvent(val coordinatesModel: CoordinatesModel) : Event
        data object FieldCloseEvent : Event

        data object PhotoFeedEvent : Event
    }

    sealed interface Effect {
        data object BackPressed : Effect
        data object UpPressed : Effect
        data class Location(val coordinatesModel: CoordinatesModel) : Effect

        data class Share(val link: String) : Effect
    }


}