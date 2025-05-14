package com.mkirdev.unsplash.details.impl

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.preview.createPhotoDetailsPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val LIKED = true
private const val UNLIKED = false
private const val UPDATED_COUNT = 0

@Stable
class PhotoDetailsViewModel : ViewModel(), PhotoDetailsContract {

    private val _uiState = MutableStateFlow<PhotoDetailsContract.State>(
        PhotoDetailsContract.State.Idle
    )

    @Stable
    override val uiState: StateFlow<PhotoDetailsContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<PhotoDetailsContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<PhotoDetailsContract.Effect?> = _effect.asStateFlow()


    init {
        try {
            // load photoDetails
            _uiState.update {
                PhotoDetailsContract.State.Success(
                    photoDetailsModel = createPhotoDetailsPreview()
                )
            }
        } catch (t: Throwable) {
            _uiState.update {
                PhotoDetailsContract.State.Failure(
                    error = t.message.toString(),
                    photoDetailsModel = null,
                    updatedCount = UPDATED_COUNT
                )
            }
        }

    }

    override fun handleEvent(event: PhotoDetailsContract.Event) {
        when (event) {
            is PhotoDetailsContract.Event.DownloadEvent -> onDownloadClick(event.link)
            is PhotoDetailsContract.Event.LocationEvent -> onLocationClick(event.coordinatesModel)
            is PhotoDetailsContract.Event.ShareEvent -> onShareClick(event.link)
            is PhotoDetailsContract.Event.PhotoLikeEvent -> onLikeClick(
                photoId = event.photoId,
                isLiked = LIKED
            )

            is PhotoDetailsContract.Event.PhotoRemoveLikeEvent -> onLikeClick(
                photoId = event.photoId,
                isLiked = UNLIKED
            )

            PhotoDetailsContract.Event.FieldCloseEvent -> onCloseFieldClick()

            PhotoDetailsContract.Event.NavigateUpEvent -> onNavigateUp()

            PhotoDetailsContract.Event.NavigateBackEvent -> onNavigateBack()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onLocationClick(coordinatesModel: CoordinatesModel) {
        _effect.update {
            PhotoDetailsContract.Effect.Location(
                coordinatesModel = coordinatesModel
            )
        }
    }

    private fun onShareClick(link: String) {
        _effect.update {
            PhotoDetailsContract.Effect.Share(
                link = link
            )
        }
    }

    private fun onLikeClick(photoId: String, isLiked: Boolean) {
        try {
            if (isLiked) {
                // send liked photo
            } else {
                // send unliked photo
            }
        } catch (e: Throwable) {
            if (_uiState.value is PhotoDetailsContract.State.Success) {
                _uiState.update {
                    PhotoDetailsContract.State.Failure(
                        error = e.message.toString(),
                        photoDetailsModel = (it as PhotoDetailsContract.State.Success).photoDetailsModel,
                        updatedCount = UPDATED_COUNT
                    )
                }
            } else if (_uiState.value is PhotoDetailsContract.State.Failure) {
                _uiState.update {
                    (it as PhotoDetailsContract.State.Failure).copy(
                        error = e.message.toString(),
                        updatedCount = it.updatedCount + 1
                    )
                }
            }
        }
    }

    private fun onCloseFieldClick() {

        when (_uiState.value) {
            is PhotoDetailsContract.State.DownloadFailure -> {
                _uiState.update {
                    PhotoDetailsContract.State.Success((it as PhotoDetailsContract.State.DownloadFailure).photoDetailsModel)
                }
            }

            is PhotoDetailsContract.State.DownloadSuccess -> {
                _uiState.update {
                    PhotoDetailsContract.State.Success((it as PhotoDetailsContract.State.DownloadSuccess).photoDetailsModel)
                }
            }
            is PhotoDetailsContract.State.Failure -> {
                (_uiState.value as PhotoDetailsContract.State.Failure).photoDetailsModel?.let { photoDetailsModel ->
                    _uiState.update {
                        PhotoDetailsContract.State.Success(photoDetailsModel)
                    }
                }
            }
            is PhotoDetailsContract.State.Success -> {}
            PhotoDetailsContract.State.Idle -> {}
        }
    }

    private fun onNavigateUp() {
        _effect.update {
            PhotoDetailsContract.Effect.UpPressed
        }
    }

    private fun onNavigateBack() {
        _effect.update {
            PhotoDetailsContract.Effect.BackPressed
        }
    }

    private fun onDownloadClick(link: String) {
        try {
            preloadingPhoto()

            // downloading photo

            successDownloadPhoto()


        } catch (e: Throwable) {
            failureDownloadPhoto()
        }
    }

    private fun preloadingPhoto() {
        if (_uiState.value is PhotoDetailsContract.State.DownloadSuccess) {
            _uiState.update {
                PhotoDetailsContract.State.Success(
                    photoDetailsModel = (it as PhotoDetailsContract.State.DownloadSuccess).photoDetailsModel
                )
            }
        } else if (_uiState.value is PhotoDetailsContract.State.DownloadFailure) {
            _uiState.update {
                PhotoDetailsContract.State.Success(
                    photoDetailsModel = (it as PhotoDetailsContract.State.DownloadFailure).photoDetailsModel
                )
            }
        }
    }

    private fun successDownloadPhoto() {
        if (_uiState.value is PhotoDetailsContract.State.Success) {
            _uiState.update {
                PhotoDetailsContract.State.DownloadSuccess(
                    photoDetailsModel = (it as PhotoDetailsContract.State.Success).photoDetailsModel
                )
            }
        } else if (_uiState.value is PhotoDetailsContract.State.Failure) {
            _uiState.update {
                PhotoDetailsContract.State.DownloadSuccess(
                    photoDetailsModel = (it as PhotoDetailsContract.State.Failure).photoDetailsModel
                        ?: throw Throwable()
                )
            }
        }
    }

    private fun failureDownloadPhoto() {
        if (_uiState.value is PhotoDetailsContract.State.Success) {
            _uiState.update {
                PhotoDetailsContract.State.DownloadFailure(
                    photoDetailsModel = (it as PhotoDetailsContract.State.Success).photoDetailsModel
                )
            }
        } else if (_uiState.value is PhotoDetailsContract.State.Failure) {
            ((_uiState.value as PhotoDetailsContract.State.Failure).photoDetailsModel)?.let { photoDetailsModel ->
                _uiState.update {
                    PhotoDetailsContract.State.DownloadFailure(
                        photoDetailsModel = photoDetailsModel
                    )
                }
            }
        }
    }


}