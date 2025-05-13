package com.mkirdev.unsplash.collections.impl

import androidx.paging.PagingData
import com.mkirdev.unsplash.collection_item.models.CollectionItemModel
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import kotlinx.coroutines.flow.Flow

interface CollectionsContract :
    UniFlowViewModel<CollectionsContract.Event, CollectionsContract.State, CollectionsContract.Effect?> {

    sealed interface State {
        data class Success(
            val collectionItemsModel: Flow<PagingData<CollectionItemModel>>,
            val isPagingLoadingError: Boolean
        ) : State

        data class Failure(
            val error: String,
        ) : State

        data object Loading : State

        data object Idle : State
    }

    sealed interface Event {

        data class CollectionDetailsEvent(val collectionId: String) : Event
        data object ErrorLoadEvent : Event
        data object FieldCloseEvent : Event
    }

    sealed interface Effect {
        data class CollectionDetails(val collectionId: String) : Effect
    }

}