package com.mkirdev.unsplash.collection_details.impl

import androidx.paging.PagingData
import com.mkirdev.unsplash.collection_details.models.CollectionDetailsModel
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.coroutines.flow.Flow

interface CollectionDetailsContract :
    UniFlowViewModel<CollectionDetailsContract.Event, CollectionDetailsContract.State, CollectionDetailsContract.Effect?> {
    sealed interface State {

        data class Success(
            val collectionDetailsModel: CollectionDetailsModel,
            val photoItemModels: Flow<PagingData<PhotoItemModel>>,
            val isPagingLoadingError: Boolean?
        ) : State

        data class Failure(
            val error: String,
            val isPagingLoadingError: Boolean?,
            val updatedCount: Int,
            val collectionDetailsModel: CollectionDetailsModel?,
            val photoItemModels: Flow<PagingData<PhotoItemModel>>?
        ) : State

        data object Loading : State

        data object Idle : State

    }

    sealed interface Event {
        data class DownloadEvent(val link: String) : Event

        data class PhotoLikeEvent(val photoId: String) : Event

        data class PhotoRemoveLikeEvent(val photoId: String) : Event

        data class PhotoDetailsEvent(val photoId: String) : Event

        data object ErrorLoadEvent : Event

        data object FieldCloseEvent : Event

        data object PagingFieldCloseEvent : Event

        data object NavigateUpEvent : Event

        data object NavigateBackEvent : Event
    }

    sealed interface Effect {
        data object BackPressed : Effect

        data object UpPressed : Effect

        data class PhotoDetails(val photoId: String) : Effect
    }
}