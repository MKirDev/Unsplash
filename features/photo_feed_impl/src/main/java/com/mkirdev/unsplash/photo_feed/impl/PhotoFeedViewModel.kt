package com.mkirdev.unsplash.photo_feed.impl

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.mkirdev.unsplash.photo_feed.preview.createPhotoFeedPreviewData
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val EMPTY_STRING = ""
private const val UPDATED_COUNT = 0
private const val LIKED = true
private const val UNLIKED = false

@Stable
internal class PhotoFeedViewModel : ViewModel(), PhotoFeedContract {

    private val _uiState = MutableStateFlow<PhotoFeedContract.State>(
        PhotoFeedContract.State.Idle
    )

    @Stable
    override val uiState: StateFlow<PhotoFeedContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<PhotoFeedContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<PhotoFeedContract.Effect?> = _effect.asStateFlow()

    init {
        try {
            _uiState.update {
                PhotoFeedContract.State.Loading
            }
            // load photos
            _uiState.update {
                PhotoFeedContract.State.Success(
                    search = EMPTY_STRING,
                    models = createPhotoFeedPreviewData()
                )
            }
        } catch (e: Throwable) {
            _uiState.update {
                PhotoFeedContract.State.Failure(
                    search = EMPTY_STRING,
                    models = persistentListOf(),
                    error = e.message.toString(),
                    updatedCount = UPDATED_COUNT
                )
            }
        }

    }

    override fun handleEvent(event: PhotoFeedContract.Event) {
        when (event) {
            PhotoFeedContract.Event.PhotosLoadingRequestedEvent -> onPhotosLoad()
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
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onFieldClose() {
        _uiState.update {
            PhotoFeedContract.State.Success(
                search = (it as PhotoFeedContract.State.Failure).search,
                models = it.models
            )
        }
    }

    private fun onPhotosLoad() {
        try {
            // load photos
            successLoadedPhotos(createPhotoFeedPreviewData())
        } catch (e: Throwable) {
            failureLoadedPhotos(e.message.toString())
        }
    }

    private fun onPhotoClick(photoId: String) {
        _effect.update {
            PhotoFeedContract.Effect.Details(photoId)
        }
    }

    private fun onPhotoSend(photoId: String, isLiked: Boolean) {
        try {
            if (isLiked) {
                // send liked photo
            } else {
                // send unliked photo
            }
            // get new list of photos
            successLoadedPhotos(createPhotoFeedPreviewData())
        } catch (e: Throwable) {
            failureLoadedPhotos(e.message.toString())
        }
    }

    private fun onSearch(text: String) {
        try {
            // searching
            // get new list of photos
            if (_uiState.value is PhotoFeedContract.State.Failure) {
                _uiState.update {
                    PhotoFeedContract.State.Success(
                        search = text,
                        models = createPhotoFeedPreviewData()
                    )
                }
            } else if (_uiState.value is PhotoFeedContract.State.Success) {
                _uiState.update {
                    (it as PhotoFeedContract.State.Success).copy(
                        search = text,
                        models = createPhotoFeedPreviewData()
                    )
                }
            }
        } catch (e: Throwable) {
            if (_uiState.value is PhotoFeedContract.State.Failure) {
                _uiState.update {
                    (it as PhotoFeedContract.State.Failure).copy(
                        search = text,
                        error = e.message.toString()
                    )
                }
            } else if (_uiState.value is PhotoFeedContract.State.Success) {
                _uiState.update {
                    PhotoFeedContract.State.Failure(
                        search = text,
                        models = (it as PhotoFeedContract.State.Success).models,
                        error = e.message.toString(),
                        updatedCount = UPDATED_COUNT
                    )
                }
            }
        }
    }

    private fun successLoadedPhotos(listOfPhotos: ImmutableList<PhotoItemModel>) {
        if (_uiState.value is PhotoFeedContract.State.Failure) {
            _uiState.update {
                PhotoFeedContract.State.Success(
                    search = (it as PhotoFeedContract.State.Failure).search,
                    models = listOfPhotos
                )
            }
        } else if (_uiState.value is PhotoFeedContract.State.Success) {
            _uiState.update {
                (it as PhotoFeedContract.State.Success).copy(
                    models = listOfPhotos
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
                    models = it.models,
                    error = error,
                    updatedCount = UPDATED_COUNT
                )
            }
        }
    }
}

internal class PhotoFeedViewModelFactory(

) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PhotoFeedViewModel() as T
    }
}