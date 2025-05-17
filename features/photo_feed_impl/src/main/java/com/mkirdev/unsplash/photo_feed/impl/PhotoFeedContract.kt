package com.mkirdev.unsplash.photo_feed.impl

import androidx.compose.runtime.Immutable
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.collections.immutable.ImmutableList

interface PhotoFeedContract : UniFlowViewModel<PhotoFeedContract.Event, PhotoFeedContract.State, PhotoFeedContract.Effect?>{

    sealed interface State {
        @Immutable
        data class Success(
            val search: String,
            val models: ImmutableList<PhotoItemModel>
        ) : State

        @Immutable
        data class Failure(
            val search: String,
            val models: ImmutableList<PhotoItemModel>,
            val error: String,
            val updatedCount: Int
        ) : State

        data object Loading : State

        data object Idle : State
    }

    sealed interface Event {
        data class SearchEvent(val search: String) : Event

        data class PhotoDetailsOpenedEvent(val photoId: String) : Event

        data class PhotoLikedEvent(val photoId: String) : Event

        data class PhotoUnlikedEvent(val photoId: String) : Event

        data object PhotosLoadingRequestedEvent : Event

        data object FieldClosedEvent : Event
    }

    sealed interface Effect {
        data class Details(val photoId: String) : Effect
    }
}