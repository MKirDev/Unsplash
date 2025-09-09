package com.mkirdev.unsplash.photo_feed.impl

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.photo_feed.mappers.toPresentation
import com.mkirdev.unsplash.photo_feed.models.ScrollState
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.coroutines.FlowPreview
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

@OptIn(FlowPreview::class)
@Stable
internal class PhotoFeedViewModel(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
) : ViewModel(), PhotoFeedContract {

    private val _uiState = MutableStateFlow<PhotoFeedContract.State>(
        PhotoFeedContract.State.Idle()
    )

    @Stable
    override val uiState: StateFlow<PhotoFeedContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<PhotoFeedContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<PhotoFeedContract.Effect?> = _effect.asStateFlow()

    init {
        loadPhotos()
    }

    override fun handleEvent(event: PhotoFeedContract.Event) {
        when (event) {
            PhotoFeedContract.Event.FieldClosedEvent -> onFieldClose()
            is PhotoFeedContract.Event.PhotoDetailsOpenedEvent -> onPhotoClick(photoId = event.photoId)
            is PhotoFeedContract.Event.PhotoLikedEvent -> onPhotoSend(
                photoId = event.photoId,
                isLiked = LIKED
            )

            is PhotoFeedContract.Event.PhotoUnlikedEvent -> onPhotoSend(
                photoId = event.photoId,
                isLiked = UNLIKED
            )

            PhotoFeedContract.Event.PhotoSearchClickedEvent -> onSearchClick()

            is PhotoFeedContract.Event.PagingRetryEvent -> onPagingRetry(event.pagedItems)

            is PhotoFeedContract.Event.ScrollStateSavedEvent -> onScrollStateSave(scrollState = event.scrollState)

            PhotoFeedContract.Event.PagingFieldClosedEvent -> onPagingFieldClose()

            PhotoFeedContract.Event.LoadingErrorEvent -> onErrorLoad()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun transformPagingData(flow: Flow<PagingData<Photo>>): Flow<PagingData<PhotoItemModel>> {
        return flow
            .map { pagingData -> pagingData.map { it.toPresentation() } }
            .cachedIn(viewModelScope)
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            try {
                val photos = transformPagingData(getPhotosUseCase.execute())
                _uiState.update {
                    PhotoFeedContract.State.Loading()
                }
                _uiState.update {
                    PhotoFeedContract.State.Success(
                        models = photos,
                        scrollState = ScrollState(),
                        isPagingLoadingError = false
                    )
                }
            } catch (t: Throwable) {
                _uiState.update {
                    PhotoFeedContract.State.Failure(
                        isPagingLoadingError = false,
                        scrollState = ScrollState(),
                        models = null,
                        error = t.message.toString(),
                        updatedCount = UPDATED_COUNT
                    )
                }
            }
        }
    }

    private fun onFieldClose() {
        if (_uiState.value is PhotoFeedContract.State.Failure) {
            _uiState.update {
                PhotoFeedContract.State.Success(
                    models = (it as PhotoFeedContract.State.Failure).models ?: throw Throwable(),
                    scrollState = it.scrollState,
                    isPagingLoadingError = it.isPagingLoadingError
                )
            }
        }
    }

    private fun onPagingFieldClose() {
        _uiState.update { currentState ->
            when (currentState) {
                is PhotoFeedContract.State.Failure ->
                    PhotoFeedContract.State.Success(
                        models = currentState.models ?: throw Throwable(),
                        scrollState = currentState.scrollState,
                        isPagingLoadingError = false
                    )

                is PhotoFeedContract.State.Success -> currentState.copy(isPagingLoadingError = false)
                else -> currentState
            }
        }
    }

    private fun onPagingRetry(pagedItems: LazyPagingItems<PhotoItemModel>) {
        pagedItems.retry()
    }

    private fun onSearchClick() {
        _effect.update {
            PhotoFeedContract.Effect.Search
        }
    }

    private fun onScrollStateSave(scrollState: ScrollState) {
        _uiState.update { currentState ->
            when (currentState) {
                is PhotoFeedContract.State.Success -> currentState.copy(
                    scrollState = scrollState
                )
                is PhotoFeedContract.State.Failure -> currentState.copy(
                    scrollState = scrollState
                )
                else -> currentState
            }
        }
    }

    private fun onErrorLoad() {
        if (_uiState.value is PhotoFeedContract.State.Success) {
            _uiState.update {
                (it as PhotoFeedContract.State.Success).copy(
                    isPagingLoadingError = true
                )
            }
        } else {
            _uiState.update {
                (it as PhotoFeedContract.State.Failure).copy(
                    isPagingLoadingError = true
                )
            }
        }
    }

    private fun onPhotoClick(photoId: String) {
        _effect.update {
            PhotoFeedContract.Effect.Details(photoId)
        }
    }

    private fun onPhotoSend(photoId: String, isLiked: Boolean) {
        viewModelScope.launch {
            try {
                if (isLiked) {
                    likePhotoLocalUseCase.execute(photoId)
                } else {
                    unlikePhotoLocalUseCase.execute(photoId)
                }
                successLoadedPhotos()
            } catch (e: Throwable) {
                failureLoadedPhotos(e.message.toString())
            }
        }
    }

    private fun successLoadedPhotos() {
        if (_uiState.value is PhotoFeedContract.State.Failure) {
            _uiState.update {
                PhotoFeedContract.State.Success(
                    models = (it as PhotoFeedContract.State.Failure).models ?: throw Throwable(),
                    scrollState = it.scrollState,
                    isPagingLoadingError = it.isPagingLoadingError
                )
            }
        }
    }

    private fun failureLoadedPhotos(error: String) {
        if (_uiState.value is PhotoFeedContract.State.Failure) {
            _uiState.update {
                (it as PhotoFeedContract.State.Failure).copy(
                    error = error,
                    updatedCount = it.updatedCount + 1
                )
            }
        } else if (_uiState.value is PhotoFeedContract.State.Success) {
            _uiState.update {
                PhotoFeedContract.State.Failure(
                    models = (it as PhotoFeedContract.State.Success).models,
                    scrollState = it.scrollState,
                    isPagingLoadingError = it.isPagingLoadingError,
                    error = error,
                    updatedCount = UPDATED_COUNT
                )
            }
        }
    }


}

internal class PhotoFeedViewModelFactory(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PhotoFeedViewModel(
            getPhotosUseCase = getPhotosUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase
        ) as T
    }
}