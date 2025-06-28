package com.mkirdev.unsplash.photo_feed.impl

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.coroutines.flow.Flow

internal interface PhotoFeedContract : UniFlowViewModel<PhotoFeedContract.Event, PhotoFeedContract.State, PhotoFeedContract.Effect?>{

    sealed interface State {
        @Immutable
        data class Success(
            val search: String,
            val models: Flow<PagingData<PhotoItemModel>>,
            val isPagingLoadingError: Boolean,
        ) : State

        @Immutable
        data class Failure(
            val search: String,
            val models: Flow<PagingData<PhotoItemModel>>?,
            val isPagingLoadingError: Boolean,
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
        data class PagingRetryEvent(val pagedItems: LazyPagingItems<PhotoItemModel>) : Event
        data object LoadingErrorEvent : Event

        data object PagingFieldClosedEvent : Event

        data object FieldClosedEvent : Event
    }

    sealed interface Effect {
        data class Details(val photoId: String) : Effect
    }
}