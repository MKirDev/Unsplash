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
class DetailsViewModel : ViewModel(), DetailsContract {

    private val _uiState = MutableStateFlow<DetailsContract.State>(
        DetailsContract.State.Idle
    )

    @Stable
    override val uiState: StateFlow<DetailsContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<DetailsContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<DetailsContract.Effect?> = _effect.asStateFlow()


    init {
        try {
            // load photoDetails
            _uiState.update {
                DetailsContract.State.Success(
                    detailsModel = createPhotoDetailsPreview()
                )
            }
        } catch (t: Throwable) {
            _uiState.update {
                DetailsContract.State.Failure(
                    error = t.message.toString(),
                    detailsModel = null,
                    updatedCount = UPDATED_COUNT
                )
            }
        }

    }

    override fun handleEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.DownloadRequestedEvent -> onDownloadClick(event.link)
            is DetailsContract.Event.LocationOpenedEvent -> onLocationClick(event.coordinatesModel)
            is DetailsContract.Event.ShareRequestedEvent -> onShareClick(event.link)
            is DetailsContract.Event.PhotoLikedEvent -> onLikeClick(
                photoId = event.photoId,
                isLiked = LIKED
            )

            is DetailsContract.Event.PhotoUnlikedEvent -> onLikeClick(
                photoId = event.photoId,
                isLiked = UNLIKED
            )

            DetailsContract.Event.FieldClosedEvent -> onCloseFieldClick()

            DetailsContract.Event.NavigateUpEvent -> onNavigateUp()

            DetailsContract.Event.NavigateBackEvent -> onNavigateBack()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onLocationClick(coordinatesModel: CoordinatesModel) {
        _effect.update {
            DetailsContract.Effect.Location(
                coordinatesModel = coordinatesModel
            )
        }
    }

    private fun onShareClick(link: String) {
        _effect.update {
            DetailsContract.Effect.Share(
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
            if (_uiState.value is DetailsContract.State.Success) {
                _uiState.update {
                    DetailsContract.State.Failure(
                        error = e.message.toString(),
                        detailsModel = (it as DetailsContract.State.Success).detailsModel,
                        updatedCount = UPDATED_COUNT
                    )
                }
            } else if (_uiState.value is DetailsContract.State.Failure) {
                _uiState.update {
                    (it as DetailsContract.State.Failure).copy(
                        error = e.message.toString(),
                        updatedCount = it.updatedCount + 1
                    )
                }
            }
        }
    }

    private fun onCloseFieldClick() {

        when (_uiState.value) {
            is DetailsContract.State.DownloadFailure -> {
                _uiState.update {
                    DetailsContract.State.Success((it as DetailsContract.State.DownloadFailure).detailsModel)
                }
            }

            is DetailsContract.State.DownloadSuccess -> {
                _uiState.update {
                    DetailsContract.State.Success((it as DetailsContract.State.DownloadSuccess).detailsModel)
                }
            }
            is DetailsContract.State.Failure -> {
                (_uiState.value as DetailsContract.State.Failure).detailsModel?.let { photoDetailsModel ->
                    _uiState.update {
                        DetailsContract.State.Success(photoDetailsModel)
                    }
                }
            }
            is DetailsContract.State.Success -> {}
            DetailsContract.State.Idle -> {}
        }
    }

    private fun onNavigateUp() {
        _effect.update {
            DetailsContract.Effect.UpPressed
        }
    }

    private fun onNavigateBack() {
        _effect.update {
            DetailsContract.Effect.BackPressed
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
        if (_uiState.value is DetailsContract.State.DownloadSuccess) {
            _uiState.update {
                DetailsContract.State.Success(
                    detailsModel = (it as DetailsContract.State.DownloadSuccess).detailsModel
                )
            }
        } else if (_uiState.value is DetailsContract.State.DownloadFailure) {
            _uiState.update {
                DetailsContract.State.Success(
                    detailsModel = (it as DetailsContract.State.DownloadFailure).detailsModel
                )
            }
        }
    }

    private fun successDownloadPhoto() {
        if (_uiState.value is DetailsContract.State.Success) {
            _uiState.update {
                DetailsContract.State.DownloadSuccess(
                    detailsModel = (it as DetailsContract.State.Success).detailsModel
                )
            }
        } else if (_uiState.value is DetailsContract.State.Failure) {
            _uiState.update {
                DetailsContract.State.DownloadSuccess(
                    detailsModel = (it as DetailsContract.State.Failure).detailsModel
                        ?: throw Throwable()
                )
            }
        }
    }

    private fun failureDownloadPhoto() {
        if (_uiState.value is DetailsContract.State.Success) {
            _uiState.update {
                DetailsContract.State.DownloadFailure(
                    detailsModel = (it as DetailsContract.State.Success).detailsModel
                )
            }
        } else if (_uiState.value is DetailsContract.State.Failure) {
            ((_uiState.value as DetailsContract.State.Failure).detailsModel)?.let { photoDetailsModel ->
                _uiState.update {
                    DetailsContract.State.DownloadFailure(
                        detailsModel = photoDetailsModel
                    )
                }
            }
        }
    }


}