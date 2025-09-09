package com.mkirdev.unsplash.photo_feed.impl

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.photo_feed.models.ScrollState
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.coroutines.flow.Flow

internal interface PhotoFeedContract :
    UniFlowViewModel<PhotoFeedContract.Event, PhotoFeedContract.State, PhotoFeedContract.Effect?> {

    sealed interface State {
        @Immutable
        data class Success(
            val models: Flow<PagingData<PhotoItemModel>>,
            val scrollState: ScrollState,
            val isPagingLoadingError: Boolean,
        ) : State

        @Immutable
        data class Failure(
            val models: Flow<PagingData<PhotoItemModel>>?,
            val scrollState: ScrollState,
            val isPagingLoadingError: Boolean,
            val error: String,
            val updatedCount: Int
        ) : State

        @Immutable
        data class Loading(
            val scrollState: ScrollState = ScrollState()
        ) : State

        @Immutable
        data class Idle(
            val scrollState: ScrollState = ScrollState()
        ) : State
    }

    sealed interface Event {

        data class PhotoDetailsOpenedEvent(val photoId: String) : Event

        data class PhotoLikedEvent(val photoId: String) : Event
        data class PhotoUnlikedEvent(val photoId: String) : Event
        data class PagingRetryEvent(val pagedItems: LazyPagingItems<PhotoItemModel>) : Event
        data class ScrollStateSavedEvent(val scrollState: ScrollState) : Event

        data object PhotoSearchClickedEvent : Event

        data object LoadingErrorEvent : Event

        data object PagingFieldClosedEvent : Event

        data object FieldClosedEvent : Event
    }

    sealed interface Effect {
        data class Details(val photoId: String) : Effect

        data object Search : Effect
    }
}