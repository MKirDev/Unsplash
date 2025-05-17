package com.mkirdev.unsplash.details.impl

import androidx.compose.runtime.Immutable
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.models.DetailsModel

interface DetailsContract : UniFlowViewModel<
        DetailsContract.Event,
        DetailsContract.State,
        DetailsContract.Effect?> {

    sealed interface State {

        @Immutable
        data class Success(
            val detailsModel: DetailsModel,
        ) : State

        @Immutable
        data class Failure(
            val error: String,
            val detailsModel: DetailsModel?,
            val updatedCount: Int
        ) : State

        @Immutable
        data class DownloadSuccess(
            val detailsModel: DetailsModel
        ) : State

        @Immutable
        data class DownloadFailure(
            val detailsModel: DetailsModel
        ) : State

        data object Idle : State

    }

    sealed interface Event {
        data class ShareRequestedEvent(val link: String) : Event

        data class DownloadRequestedEvent(val link: String) : Event

        data class PhotoLikedEvent(val photoId: String) : Event

        data class PhotoUnlikedEvent(val photoId: String) : Event

        data class LocationOpenedEvent(val coordinatesModel: CoordinatesModel) : Event

        data object FieldClosedEvent : Event

        data object NavigateUpEvent : Event

        data object NavigateBackEvent : Event
    }

    sealed interface Effect {
        data object BackPressed : Effect
        data object UpPressed : Effect
        data class Location(val coordinatesModel: CoordinatesModel) : Effect

        data class Share(val link: String) : Effect
    }


}