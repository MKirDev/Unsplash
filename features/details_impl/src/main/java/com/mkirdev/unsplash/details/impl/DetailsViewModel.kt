package com.mkirdev.unsplash.details.impl

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mkirdev.unsplash.details.mappers.toDetailsModel
import com.mkirdev.unsplash.details.mappers.toPhotoItemModel
import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val LIKED = true
private const val UNLIKED = false
private const val UPDATED_COUNT = 0
private const val EMPTY_STRING = ""

@Stable
class DetailsViewModel(
    photoId: String,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
    private val addDownloadLinkUseCase: AddDownloadLinkUseCase
) : ViewModel(), DetailsContract {

    private val _uiState = MutableStateFlow<DetailsContract.State>(
        DetailsContract.State.Idle
    )

    @Stable
    override val uiState: StateFlow<DetailsContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<DetailsContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<DetailsContract.Effect?> = _effect.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                val photo = getPhotoUseCase.execute(photoId ?: EMPTY_STRING)
                _uiState.update {
                    DetailsContract.State.Success(
                        detailsModel = photo.toDetailsModel(photo.toPhotoItemModel())
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
        viewModelScope.launch {
            try {
                if (isLiked) {
                    likePhotoLocalUseCase.execute(photoId)
                } else {
                    unlikePhotoLocalUseCase.execute(photoId)
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
    }

    private fun onCloseFieldClick() {

        when (_uiState.value) {
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
        viewModelScope.launch {
            try {
                preloadingPhoto()
                addDownloadLinkUseCase.execute(link)
            } catch (e: Throwable) {
                failureDownloadPhoto(e.message.toString())
            }
        }
    }

    private fun preloadingPhoto() {
        if (_uiState.value is DetailsContract.State.Failure) {
            _uiState.update {
                DetailsContract.State.Success(
                    detailsModel = (it as DetailsContract.State.Success).detailsModel
                )
            }
        }
    }

    private fun failureDownloadPhoto(error: String) {
        if (_uiState.value is DetailsContract.State.Success) {
            _uiState.update {
                DetailsContract.State.Failure(
                    error = error,
                    detailsModel = (it as DetailsContract.State.Success).detailsModel,
                    updatedCount = UPDATED_COUNT
                )
            }
        } else if (_uiState.value is DetailsContract.State.Failure) {
            ((_uiState.value as DetailsContract.State.Failure).detailsModel)?.let { photoDetailsModel ->
                _uiState.update {
                    (it as DetailsContract.State.Failure).copy(
                        updatedCount = it.updatedCount + 1
                    )
                }
            }
        }
    }
}

internal class DetailsViewModelFactory @AssistedInject constructor(
    @Assisted private val photoId: String,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
    private val addDownloadLinkUseCase: AddDownloadLinkUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return DetailsViewModel(
            photoId = photoId,
            getPhotoUseCase = getPhotoUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase,
            addDownloadLinkUseCase = addDownloadLinkUseCase
        ) as T
    }
}

@AssistedFactory
internal interface DetailsViewModelFactoryAssisted {
    fun create(id: String): DetailsViewModelFactory
}