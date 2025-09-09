package com.mkirdev.unsplash.photo_search.impl

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
import com.mkirdev.unsplash.domain.models.PhotoSearch
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.search.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_search.mappers.toPresentation
import com.mkirdev.unsplash.photo_search.models.ScrollState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val UPDATED_COUNT = 0
private const val LIKED = true
private const val UNLIKED = false
private const val DEBOUNCE_SEARCH = 700L

@OptIn(FlowPreview::class)
@Stable
internal class PhotoSearchViewModel(
    private val searchPhotosUseCase: SearchPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
) : ViewModel(), PhotoSearchContract {

    private val _uiState = MutableStateFlow<PhotoSearchContract.State>(
        PhotoSearchContract.State.Idle()
    )

    @Stable
    override val uiState: StateFlow<PhotoSearchContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<PhotoSearchContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<PhotoSearchContract.Effect?> = _effect.asStateFlow()

    private val searchState = MutableStateFlow(PhotoSearch())

    init {
        observeSearch()
    }

    override fun handleEvent(event: PhotoSearchContract.Event) {
        when (event) {
            is PhotoSearchContract.Event.SearchEvent -> onSearch(event.search)
            is PhotoSearchContract.Event.PhotoDetailsOpenedEvent -> onPhotoClick(photoId = event.photoId)
            is PhotoSearchContract.Event.PhotoLikedEvent -> onPhotoSend(
                photoId = event.photoId,
                isLiked = LIKED
            )

            is PhotoSearchContract.Event.PhotoUnlikedEvent -> onPhotoSend(
                photoId = event.photoId,
                isLiked = UNLIKED
            )

            is PhotoSearchContract.Event.PagingRetryEvent -> onPagingRetry(event.pagedItems)

            is PhotoSearchContract.Event.ScrollStateSavedEvent -> onScrollStateSave(event.scrollState)

            PhotoSearchContract.Event.PhotoFeedClickedEvent -> onPhotoFeedClick()

            PhotoSearchContract.Event.FieldClosedEvent -> onFieldClose()

            PhotoSearchContract.Event.PagingFieldClosedEvent -> onPagingFieldClose()

            PhotoSearchContract.Event.LoadingErrorEvent -> onErrorLoad()
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

    private fun onFieldClose() {
        if (_uiState.value is PhotoSearchContract.State.Failure) {
            _uiState.update {
                PhotoSearchContract.State.Success(
                    search = (it as PhotoSearchContract.State.Failure).search,
                    models = it.models,
                    isPagingLoadingError = it.isPagingLoadingError
                )
            }
        }
    }

    private fun onPagingFieldClose() {
        _uiState.update { currentState ->
            when (currentState) {
                is PhotoSearchContract.State.Failure ->
                    PhotoSearchContract.State.Success(
                        search = currentState.search,
                        models = currentState.models,
                        isPagingLoadingError = false
                    )

                is PhotoSearchContract.State.Success -> currentState.copy(isPagingLoadingError = false)
                else -> currentState
            }
        }
    }

    private fun onPagingRetry(pagedItems: LazyPagingItems<PhotoItemModel>) {
        pagedItems.retry()
    }

    private fun onErrorLoad() {
        if (_uiState.value is PhotoSearchContract.State.Success) {
            _uiState.update {
                (it as PhotoSearchContract.State.Success).copy(
                    isPagingLoadingError = true
                )
            }
        } else {
            _uiState.update {
                (it as PhotoSearchContract.State.Failure).copy(
                    isPagingLoadingError = true
                )
            }
        }
    }

    private fun onScrollStateSave(scrollState: ScrollState) {
        _uiState.update { currentState ->
            when (currentState) {
                is PhotoSearchContract.State.Success -> currentState.copy(
                    scrollState = scrollState
                )

                is PhotoSearchContract.State.Failure -> currentState.copy(
                    scrollState = scrollState
                )

                else -> currentState
            }
        }
    }

    private fun onSearch(text: String) {
        _uiState.update { currentState ->
            when (currentState) {
                is PhotoSearchContract.State.Failure -> {
                    PhotoSearchContract.State.Success(
                        search = text,
                        models = currentState.models,
                        isPagingLoadingError = currentState.isPagingLoadingError
                    )
                }
                is PhotoSearchContract.State.Success -> {
                    currentState.copy(
                        search = text
                    )
                }
                is PhotoSearchContract.State.Loading -> {
                    currentState.copy(
                        search = text
                    )
                }
                is PhotoSearchContract.State.Idle -> {
                    currentState.copy(
                        search = text
                    )
                }
            }
        }

        searchState.update {
            it.copy(search = text)
        }
    }

    private fun onPhotoClick(photoId: String) {
        _effect.update {
            PhotoSearchContract.Effect.Details(photoId)
        }
    }

    private fun onPhotoFeedClick() {
        _effect.update {
            PhotoSearchContract.Effect.Feed
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
        if (_uiState.value is PhotoSearchContract.State.Failure) {
            _uiState.update {
                PhotoSearchContract.State.Success(
                    search = (it as PhotoSearchContract.State.Failure).search,
                    models = it.models,
                    isPagingLoadingError = it.isPagingLoadingError
                )
            }
        }
    }

    private fun failureLoadedPhotos(error: String) {
        if (_uiState.value is PhotoSearchContract.State.Failure) {
            _uiState.update {
                (it as PhotoSearchContract.State.Failure).copy(
                    error = error,
                    updatedCount = it.updatedCount + 1
                )
            }
        } else if (_uiState.value is PhotoSearchContract.State.Success) {
            _uiState.update {
                PhotoSearchContract.State.Failure(
                    search = (it as PhotoSearchContract.State.Success).search,
                    isPagingLoadingError = it.isPagingLoadingError,
                    models = it.models,
                    error = error,
                    updatedCount = UPDATED_COUNT
                )
            }
        }
    }

    private fun observeSearch() {
        viewModelScope.launch {
            searchState
                .debounce(DEBOUNCE_SEARCH)
                .distinctUntilChanged()
                .filter { it.search.isNotEmpty() }
                .map {
                    searchPhotosUseCase.execute(it.search)
                }
                .catch { throwable ->
                    if (_uiState.value is PhotoSearchContract.State.Failure) {
                        _uiState.update {
                            (it as PhotoSearchContract.State.Failure).copy(
                                error = throwable.message.toString()
                            )
                        }
                    } else if (_uiState.value is PhotoSearchContract.State.Success) {
                        _uiState.update {
                            PhotoSearchContract.State.Failure(
                                search = (it as PhotoSearchContract.State.Success).search,
                                models = it.models,
                                isPagingLoadingError = it.isPagingLoadingError,
                                error = throwable.message.toString(),
                                updatedCount = UPDATED_COUNT
                            )
                        }
                    }
                }
                .collect { flowPagingData ->
                    val photos = transformPagingData(flowPagingData)
                    _uiState.update { currentState ->
                        when (currentState) {
                            is PhotoSearchContract.State.Success -> currentState.copy(
                                models = photos
                            )
                            else -> {
                                PhotoSearchContract.State.Success(
                                    search = searchState.value.search,
                                    models = photos,
                                    isPagingLoadingError = false
                                )
                            }
                        }

                    }
                }
        }
    }
}

internal class PhotoSearchViewModelFactory(
    private val searchPhotosUseCase: SearchPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PhotoSearchViewModel(
            searchPhotosUseCase = searchPhotosUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase
        ) as T
    }
}