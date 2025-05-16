package com.mkirdev.unsplash.collection_details.impl

import androidx.lifecycle.ViewModel
import com.mkirdev.unsplash.collection_details.preview.createCollectionDetailsPreviewData
import com.mkirdev.unsplash.collection_details.preview.createPhotoItemModelsPreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val UPDATED_COUNT = 0
private const val LIKED = true
private const val UNLIKED = false

class CollectionDetailsViewModel : ViewModel(), CollectionDetailsContract {

    private val _uiState =
        MutableStateFlow<CollectionDetailsContract.State>(CollectionDetailsContract.State.Idle)
    override val uiState: StateFlow<CollectionDetailsContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<CollectionDetailsContract.Effect?>(null)
    override val effect: StateFlow<CollectionDetailsContract.Effect?> = _effect.asStateFlow()

    init {
        try {
            _uiState.update {
                CollectionDetailsContract.State.Loading
            }
            // load photos
            _uiState.update {
                CollectionDetailsContract.State.Success(
                    collectionDetailsModel = createCollectionDetailsPreviewData(),
                    photoItemModels = createPhotoItemModelsPreviewData(),
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

    override fun handleEvent(event: CollectionDetailsContract.Event) {
        when (event) {
            is CollectionDetailsContract.Event.DownloadEvent -> onDownloadClick(event.link)
            is CollectionDetailsContract.Event.PhotoLikeEvent -> onLikeClick(event.photoId, LIKED)
            is CollectionDetailsContract.Event.PhotoRemoveLikeEvent -> onLikeClick(
                event.photoId,
                UNLIKED
            )
            is CollectionDetailsContract.Event.PhotoDetailsEvent -> onPhotoDetails(event.photoId)
            CollectionDetailsContract.Event.ErrorLoadEvent -> onErrorLoad()
            CollectionDetailsContract.Event.FieldCloseEvent -> onCloseFieldClick()
            CollectionDetailsContract.Event.PagingFieldCloseEvent -> onPagingCloseFieldClick()
            CollectionDetailsContract.Event.NavigateBackEvent -> onNavigateBack()
            CollectionDetailsContract.Event.NavigateUpEvent -> onNavigateUp()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onDownloadClick(link: String) {
        try {
            // downloading
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

    private fun onLikeClick(photoId: String, isLiked: Boolean) {
        try {
            if (isLiked) {
                // send liked photo
            } else {
                // send unliked photo
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
        _uiState.update {
            CollectionDetailsContract.State.Success(
                collectionDetailsModel = (it as CollectionDetailsContract.State.Failure).collectionDetailsModel
                    ?: throw Throwable(),
                photoItemModels = it.photoItemModels ?: throw Throwable(),
                isPagingLoadingError = it.isPagingLoadingError
            )
        }
    }

    private fun onPagingCloseFieldClick() {
        _uiState.update {
            (it as CollectionDetailsContract.State.Success).copy(
                isPagingLoadingError = false
            )
        }
        _uiState.update {
            (it as CollectionDetailsContract.State.Success).copy(
                isPagingLoadingError = null
            )
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