package com.mkirdev.unsplash.photo_search.impl

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_search.models.ScrollState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val EMPTY_STRING = ""
internal interface PhotoSearchContract :
    UniFlowViewModel<PhotoSearchContract.Event, PhotoSearchContract.State, PhotoSearchContract.Effect?> {

    sealed interface State {
        @Immutable
        data class Success(
            val search: String,
            val models: Flow<PagingData<PhotoItemModel>>,
            val scrollState: ScrollState = ScrollState(),
            val isPagingLoadingError: Boolean,
        ) : State

        @Immutable
        data class Failure(
            val search: String,
            val models: Flow<PagingData<PhotoItemModel>>,
            val scrollState: ScrollState = ScrollState(),
            val isPagingLoadingError: Boolean,
            val error: String,
            val updatedCount: Int
        ) : State

        data class Loading(
            val search: String = EMPTY_STRING,
            val models: Flow<PagingData<PhotoItemModel>> = flow { PagingData.empty<PhotoItemModel>() },
            val scrollState: ScrollState = ScrollState()
        ) : State

        data class Idle(
            val search: String = EMPTY_STRING,
            val models: Flow<PagingData<PhotoItemModel>> = flow { PagingData.empty<PhotoItemModel>() },
            val scrollState: ScrollState = ScrollState()
        ) : State
    }

    sealed interface Event {

        data class SearchEvent(val search: String) : Event

        data class PhotoDetailsOpenedEvent(val photoId: String) : Event

        data class PhotoLikedEvent(val photoId: String) : Event

        data class PhotoUnlikedEvent(val photoId: String) : Event
        data class PagingRetryEvent(val pagedItems: LazyPagingItems<PhotoItemModel>) : Event

        data class ScrollStateSavedEvent(val scrollState: ScrollState) : Event

        data object PhotoFeedClickedEvent : Event
        data object LoadingErrorEvent : Event

        data object PagingFieldClosedEvent : Event

        data object FieldClosedEvent : Event
    }

    sealed interface Effect {
        data class Details(val photoId: String) : Effect

        data object Feed : Effect
    }
}