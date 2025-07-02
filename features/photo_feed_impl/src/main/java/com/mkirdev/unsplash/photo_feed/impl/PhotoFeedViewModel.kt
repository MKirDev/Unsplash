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
import com.mkirdev.unsplash.domain.models.PhotoSearch
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.photo_feed.mappers.toPresentation
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val EMPTY_STRING = ""
private const val UPDATED_COUNT = 0
private const val LIKED = true
private const val UNLIKED = false
private const val DEBOUNCE_SEARCH = 700L

@OptIn(FlowPreview::class)
@Stable
internal class PhotoFeedViewModel(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel(), PhotoFeedContract {

    private val _uiState = MutableStateFlow<PhotoFeedContract.State>(
        PhotoFeedContract.State.Idle
    )

    @Stable
    override val uiState: StateFlow<PhotoFeedContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<PhotoFeedContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<PhotoFeedContract.Effect?> = _effect.asStateFlow()

    private val searchState = MutableStateFlow(PhotoSearch())

    init {
        loadPhotos()
        observeSearch()
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

            is PhotoFeedContract.Event.SearchEvent -> onSearch(text = event.search)

            is PhotoFeedContract.Event.PagingRetryEvent -> onPagingRetry(event.pagedItems)

            PhotoFeedContract.Event.PagingFieldClosedEvent -> onPagingFieldClose()

            PhotoFeedContract.Event.LoadingErrorEvent -> onErrorLoad()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    PhotoFeedContract.State.Loading
                }
                val photos = getPhotosUseCase.execute().map { value: PagingData<Photo> ->
                    value.map { it.toPresentation() }
                }.cachedIn(viewModelScope)
                _uiState.update {
                    PhotoFeedContract.State.Success(
                        search = EMPTY_STRING,
                        models = photos,
                        isPagingLoadingError = false
                    )
                }
            } catch (t: Throwable) {
                _uiState.update {
                    PhotoFeedContract.State.Failure(
                        search = EMPTY_STRING,
                        isPagingLoadingError = false,
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
                    search = (it as PhotoFeedContract.State.Failure).search,
                    models = it.models ?: throw Throwable(),
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
                        search = currentState.search,
                        models = currentState.models ?: throw Throwable(),
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

    private fun onSearch(text: String) {
        if (_uiState.value is PhotoFeedContract.State.Failure) {
            _uiState.update {
                PhotoFeedContract.State.Success(
                    search = text,
                    models = (it as PhotoFeedContract.State.Failure).models ?: throw Throwable(),
                    isPagingLoadingError = it.isPagingLoadingError
                )
            }
        } else if (_uiState.value is PhotoFeedContract.State.Success) {
            _uiState.update {
                (it as PhotoFeedContract.State.Success).copy(
                    search = text
                )
            }
        }
        searchState.update {
            it.copy(search = text)
        }
    }

    private fun successLoadedPhotos() {
        if (_uiState.value is PhotoFeedContract.State.Failure) {
            _uiState.update {
                PhotoFeedContract.State.Success(
                    search = (it as PhotoFeedContract.State.Failure).search,
                    models = it.models ?: throw Throwable(),
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
                    search = (it as PhotoFeedContract.State.Success).search,
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
                .map {
                    if (it.search.isEmpty()) getPhotosUseCase.execute()
                    else searchPhotosUseCase.execute(query = it.search)
                }
                .catch { throwable ->
                    if (_uiState.value is PhotoFeedContract.State.Failure) {
                        _uiState.update {
                            (it as PhotoFeedContract.State.Failure).copy(
                                error = throwable.message.toString()
                            )
                        }
                    } else if (_uiState.value is PhotoFeedContract.State.Success) {
                        _uiState.update {
                            PhotoFeedContract.State.Failure(
                                search = (it as PhotoFeedContract.State.Success).search,
                                models = it.models,
                                isPagingLoadingError = it.isPagingLoadingError,
                                error = throwable.message.toString(),
                                updatedCount = UPDATED_COUNT
                            )
                        }
                    }
                }
                .collect { flowPagingData ->
                    val photos = flowPagingData.map { value: PagingData<Photo> ->
                        value.map { it.toPresentation() }
                    }.cachedIn(viewModelScope)
                    _uiState.update {
                        (it as PhotoFeedContract.State.Success).copy(
                            models = photos
                        )
                    }
                }
        }
    }
}

internal class PhotoFeedViewModelFactory(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PhotoFeedViewModel(
            getPhotosUseCase = getPhotosUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase,
            searchPhotosUseCase = searchPhotosUseCase
        ) as T
    }
}