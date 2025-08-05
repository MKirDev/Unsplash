package com.mkirdev.unsplash.collection_details.impl

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.mkirdev.unsplash.collection_details.mappers.toPresentation
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionInfoUseCase
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val UPDATED_COUNT = 0
private const val LIKED = true
private const val UNLIKED = false

@Stable
internal class CollectionDetailsViewModel(
    collectionId: String,
    private val getCollectionInfoUseCase: GetCollectionInfoUseCase,
    private val getCollectionPhotosUseCase: GetCollectionPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
    private val addDownloadLinkUseCase: AddDownloadLinkUseCase
) : ViewModel(), CollectionDetailsContract {

    private val _uiState =
        MutableStateFlow<CollectionDetailsContract.State>(CollectionDetailsContract.State.Idle)
    @Stable
    override val uiState: StateFlow<CollectionDetailsContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<CollectionDetailsContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<CollectionDetailsContract.Effect?> = _effect.asStateFlow()

    init {
        loadData(collectionId = collectionId)
    }

    private fun loadData(collectionId: String) {
        viewModelScope.launch {
            try {
                _uiState.update {
                    CollectionDetailsContract.State.Loading
                }

                val collectionInfo = getCollectionInfoUseCase.execute(collectionId = collectionId)
                val photos = getCollectionPhotosUseCase.execute(collectionId = collectionId)

                _uiState.update {
                    CollectionDetailsContract.State.Success(
                        collectionDetailsModel = collectionInfo.toPresentation(),
                        photoItemModels = transformPagingData(photos),
                        isPagingLoadingError = false
                    )
                }
            } catch (t: Throwable) {
                CollectionDetailsContract.State.Failure(
                    error = t.message.toString(),
                    isPagingLoadingError = false,
                    updatedCount = UPDATED_COUNT,
                    collectionDetailsModel = null,
                    photoItemModels = null
                )
            }
        }
    }

    private fun transformPagingData(flow: Flow<PagingData<Photo>>): Flow<PagingData<PhotoItemModel>> {
        return flow
            .map { pagingData -> pagingData.map { it.toPresentation() } }
            .cachedIn(viewModelScope)
        
    }

    override fun handleEvent(event: CollectionDetailsContract.Event) {
        when (event) {
            is CollectionDetailsContract.Event.DownloadRequestedEvent -> onDownloadClick(event.link)
            is CollectionDetailsContract.Event.PhotoLikedEvent -> onLikeClick(event.photoId, LIKED)
            is CollectionDetailsContract.Event.PhotoUnlikedEvent -> onLikeClick(
                event.photoId,
                UNLIKED
            )

            is CollectionDetailsContract.Event.PhotoDetailsOpenedEvent -> onPhotoDetails(event.photoId)
            is CollectionDetailsContract.Event.PagingRetryEvent -> onPagingRetry(event.pagedItems)
            CollectionDetailsContract.Event.LoadingErrorEvent -> onErrorLoad()
            CollectionDetailsContract.Event.FieldClosedEvent -> onCloseFieldClick()
            CollectionDetailsContract.Event.PagingFieldClosedEvent -> onPagingCloseFieldClick()
            CollectionDetailsContract.Event.NavigateBackEvent -> onNavigateBack()
            CollectionDetailsContract.Event.NavigateUpEvent -> onNavigateUp()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onDownloadClick(link: String) {
        viewModelScope.launch {
            try {
                addDownloadLinkUseCase.execute(link)
                if (_uiState.value is CollectionDetailsContract.State.Failure) {
                    _uiState.update {
                        CollectionDetailsContract.State.Success(
                            collectionDetailsModel = (it as CollectionDetailsContract.State.Failure).collectionDetailsModel
                                ?: throw Throwable(),
                            photoItemModels = it.photoItemModels ?: throw Throwable(),
                            isPagingLoadingError = it.isPagingLoadingError
                        )
                    }
                }
            } catch (t: Throwable) {
                if (_uiState.value is CollectionDetailsContract.State.Success) {
                    _uiState.update {
                        CollectionDetailsContract.State.Failure(
                            error = t.message.toString(),
                            isPagingLoadingError = (it as CollectionDetailsContract.State.Success).isPagingLoadingError,
                            updatedCount = UPDATED_COUNT,
                            collectionDetailsModel = it.collectionDetailsModel,
                            photoItemModels = it.photoItemModels
                        )
                    }
                } else {
                    _uiState.update {
                        (it as CollectionDetailsContract.State.Failure).copy(
                            error = t.message.toString(),
                            updatedCount = it.updatedCount + 1
                        )
                    }
                }
            }
        }
    }

    private fun onLikeClick(photoId: String, isLiked: Boolean) {
       viewModelScope.launch {
            try {
                if (isLiked) {
                    likePhotoLocalUseCase.execute(photoId)
                } else {
                    unlikePhotoLocalUseCase.execute(photoId)
                }
                if (_uiState.value is CollectionDetailsContract.State.Failure) {
                    _uiState.update {
                        CollectionDetailsContract.State.Success(
                            collectionDetailsModel = (it as CollectionDetailsContract.State.Failure).collectionDetailsModel
                                ?: throw Throwable(),
                            photoItemModels = it.photoItemModels ?: throw Throwable(),
                            isPagingLoadingError = it.isPagingLoadingError
                        )
                    }
                }
            } catch (t: Throwable) {
                if (_uiState.value is CollectionDetailsContract.State.Success) {
                    _uiState.update {
                        CollectionDetailsContract.State.Failure(
                            error = t.message.toString(),
                            isPagingLoadingError = (it as CollectionDetailsContract.State.Success).isPagingLoadingError,
                            updatedCount = UPDATED_COUNT,
                            collectionDetailsModel = it.collectionDetailsModel,
                            photoItemModels = it.photoItemModels
                        )
                    }
                } else {
                    _uiState.update {
                        (it as CollectionDetailsContract.State.Failure).copy(
                            error = t.message.toString(),
                            updatedCount = it.updatedCount + 1
                        )
                    }
                }
            }
        }
    }

    private fun onPagingRetry(pagedItems: LazyPagingItems<PhotoItemModel>) {
        pagedItems.retry()
    }

    private fun onErrorLoad() {
        if (_uiState.value is CollectionDetailsContract.State.Success) {
            _uiState.update {
                (it as CollectionDetailsContract.State.Success).copy(
                    isPagingLoadingError = true
                )
            }
        } else {
            _uiState.update {
                (it as CollectionDetailsContract.State.Failure).copy(
                    isPagingLoadingError = true
                )
            }
        }
    }

    private fun onCloseFieldClick() {
        if (_uiState.value is CollectionDetailsContract.State.Failure) {
            _uiState.update {
                CollectionDetailsContract.State.Success(
                    collectionDetailsModel = (it as CollectionDetailsContract.State.Failure).collectionDetailsModel
                        ?: throw Throwable(),
                    photoItemModels = it.photoItemModels ?: throw Throwable(),
                    isPagingLoadingError = it.isPagingLoadingError
                )
            }
        }
    }

    private fun onPagingCloseFieldClick() {
        _uiState.update { currentState ->
            when (currentState) {
                is CollectionDetailsContract.State.Failure -> {
                    CollectionDetailsContract.State.Success(
                        collectionDetailsModel = currentState.collectionDetailsModel
                            ?: throw Throwable(),
                        photoItemModels = currentState.photoItemModels ?: throw Throwable(),
                        isPagingLoadingError = false
                    )
                }

                is CollectionDetailsContract.State.Success -> {
                    currentState.copy(isPagingLoadingError = false)
                }

                else -> currentState
            }
        }
    }

    private fun onPhotoDetails(photoId: String) {
        _effect.update {
            CollectionDetailsContract.Effect.PhotoDetails(photoId)
        }
    }

    private fun onNavigateUp() {
        _effect.update {
            CollectionDetailsContract.Effect.UpPressed
        }
    }

    private fun onNavigateBack() {
        _effect.update {
            CollectionDetailsContract.Effect.BackPressed
        }
    }

}

internal class CollectionDetailsViewModelFactory @AssistedInject constructor(
    @Assisted private val collectionId: String,
    private val getCollectionInfoUseCase: GetCollectionInfoUseCase,
    private val getCollectionPhotosUseCase: GetCollectionPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
    private val addDownloadLinkUseCase: AddDownloadLinkUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return CollectionDetailsViewModel(
            collectionId = collectionId,
            getCollectionInfoUseCase = getCollectionInfoUseCase,
            getCollectionPhotosUseCase = getCollectionPhotosUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase,
            addDownloadLinkUseCase = addDownloadLinkUseCase
        ) as T
    }
}

@AssistedFactory
internal interface CollectionDetailsViewModelFactoryAssisted {
    fun create(collectionId: String): CollectionDetailsViewModelFactory
}