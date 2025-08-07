package com.mkirdev.unsplash.profile.impl

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.profile.models.ProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileContract : UniFlowViewModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect?> {

    sealed interface State {

        data class Success(
            val profileModel: ProfileModel,
            val photoItemModels: Flow<PagingData<PhotoItemModel>>,
            val isPagingLoadingError: Boolean,
            val isExitEnabled: Boolean
        ) : State

        data class Failure(
            val error: String,
            val isPagingLoadingError: Boolean,
            val isExitEnabled: Boolean,
            val updatedCount: Int,
            val profileModel: ProfileModel?,
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

        data object LogoutRequestedEvent : Event

        data object LogoutCanceledEvent : Event

        data object LogoutConfirmedEvent : Event

    }

    sealed interface Effect {
        data object Exit : Effect
        data class PhotoDetails(val photoId: String) : Effect
    }

}