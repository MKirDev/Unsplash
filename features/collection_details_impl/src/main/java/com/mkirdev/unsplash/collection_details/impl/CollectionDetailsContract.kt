package com.mkirdev.unsplash.collection_details.impl

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.mkirdev.unsplash.collection_details.models.CollectionDetailsModel
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.coroutines.flow.Flow

interface CollectionDetailsContract :
    UniFlowViewModel<CollectionDetailsContract.Event, CollectionDetailsContract.State, CollectionDetailsContract.Effect?> {
    sealed interface State {

        @Immutable
        data class Success(
            val collectionDetailsModel: CollectionDetailsModel,
            val photoItemModels: Flow<PagingData<PhotoItemModel>>,
            val isPagingLoadingError: Boolean
        ) : State

        @Immutable
        data class Failure(
            val error: String,
            val isPagingLoadingError: Boolean,
            val updatedCount: Int,
            val collectionDetailsModel: CollectionDetailsModel?,
            val photoItemModels: Flow<PagingData<PhotoItemModel>>?
        ) : State

        data object Loading : State

        data object Idle : State

    }

    sealed interface Event {
        data class DownloadRequestedEvent(val link: String) : Event

        data class PhotoLikedEvent(val photoId: String) : Event

        data class PhotoUnlikedEvent(val photoId: String) : Event

        data class PhotoDetailsOpenedEvent(val photoId: String) : Event

        data class PagingRetryEvent(val pagedItems: LazyPagingItems<PhotoItemModel>) : Event

        data object LoadingErrorEvent : Event

        data object FieldClosedEvent : Event

        data object PagingFieldClosedEvent : Event

        data object NavigateUpEvent : Event

        data object NavigateBackEvent : Event
    }

    sealed interface Effect {
        data object BackPressed : Effect

        data object UpPressed : Effect

        data class PhotoDetails(val photoId: String) : Effect
    }
}