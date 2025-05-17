package com.mkirdev.unsplash.profile.impl

import androidx.lifecycle.ViewModel
import com.mkirdev.unsplash.profile.preview.createPhotoItemModelsPreviewData
import com.mkirdev.unsplash.profile.preview.createProfileModelPreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val UPDATED_COUNT = 0
private const val LIKED = true
private const val UNLIKED = false

class ProfileViewModel : ViewModel(), ProfileContract {

    private val _uiState = MutableStateFlow<ProfileContract.State>(ProfileContract.State.Idle)
    override val uiState: StateFlow<ProfileContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<ProfileContract.Effect?>(null)
    override val effect: StateFlow<ProfileContract.Effect?> = _effect.asStateFlow()

    init {
        try {
            _uiState.update {
                ProfileContract.State.Loading
            }
            // load photos
            _uiState.update {
                ProfileContract.State.Success(
                    profileModel = createProfileModelPreviewData(),
                    photoItemModels = createPhotoItemModelsPreviewData(),
                    isPagingLoadingError = false,
                    isExitEnabled = false
                )
            }
        } catch (t: Throwable) {
            ProfileContract.State.Failure(
                error = t.message.toString(),
                isPagingLoadingError = false,
                isExitEnabled = false,
                updatedCount = UPDATED_COUNT,
                profileModel = null,
                photoItemModels = null
            )
        }

    }

    override fun handleEvent(event: ProfileContract.Event) {
        when (event) {
            is ProfileContract.Event.DownloadRequestedEvent -> onDownloadClick(event.link)
            is ProfileContract.Event.PhotoLikedEvent -> onLikeClick(event.photoId, LIKED)
            is ProfileContract.Event.PhotoUnlikedEvent -> onLikeClick(event.photoId, UNLIKED)
            is ProfileContract.Event.PhotoDetailsOpenedEvent -> onPhotoDetails(event.photoId)
            ProfileContract.Event.LoadingErrorEvent -> onErrorLoad()
            ProfileContract.Event.FieldClosedEvent -> onCloseFieldClick()
            ProfileContract.Event.PagingFieldClosedEvent -> onPagingCloseFieldClick()
            ProfileContract.Event.LogoutRequestedEvent -> onExitRequest()
            ProfileContract.Event.LogoutCanceledEvent -> onExitCanceled()
            ProfileContract.Event.LogoutConfirmedEvent -> onExitConfirmed()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onDownloadClick(link: String) {
        try {
            // downloading
            if (_uiState.value is ProfileContract.State.Failure) {
                _uiState.update {
                    ProfileContract.State.Success(
                        profileModel = (it as ProfileContract.State.Failure).profileModel
                            ?: throw Throwable(),
                        photoItemModels = it.photoItemModels ?: throw Throwable(),
                        isPagingLoadingError = it.isPagingLoadingError,
                        isExitEnabled = it.isExitEnabled
                    )
                }
            }
        } catch (t: Throwable) {
            if (_uiState.value is ProfileContract.State.Success) {
                _uiState.update {
                    ProfileContract.State.Failure(
                        error = t.message.toString(),
                        isPagingLoadingError = (it as ProfileContract.State.Success).isPagingLoadingError,
                        isExitEnabled = it.isExitEnabled,
                        updatedCount = UPDATED_COUNT,
                        profileModel = it.profileModel,
                        photoItemModels = it.photoItemModels
                    )
                }
            } else {
                _uiState.update {
                    (it as ProfileContract.State.Failure).copy(
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
            if (_uiState.value is ProfileContract.State.Failure) {
                _uiState.update {
                    ProfileContract.State.Success(
                        profileModel = (it as ProfileContract.State.Failure).profileModel
                            ?: throw Throwable(),
                        photoItemModels = it.photoItemModels ?: throw Throwable(),
                        isPagingLoadingError = it.isPagingLoadingError,
                        isExitEnabled = it.isExitEnabled
                    )
                }
            }
        } catch (t: Throwable) {
            if (_uiState.value is ProfileContract.State.Success) {
                _uiState.update {
                    ProfileContract.State.Failure(
                        error = t.message.toString(),
                        isPagingLoadingError = (it as ProfileContract.State.Success).isPagingLoadingError,
                        isExitEnabled = it.isExitEnabled,
                        updatedCount = UPDATED_COUNT,
                        profileModel = it.profileModel,
                        photoItemModels = it.photoItemModels
                    )
                }
            } else {
                _uiState.update {
                    (it as ProfileContract.State.Failure).copy(
                        error = t.message.toString(),
                        updatedCount = it.updatedCount + 1
                    )
                }
            }
        }
    }

    private fun onErrorLoad() {
        if (_uiState.value is ProfileContract.State.Success) {
            _uiState.update {
                (it as ProfileContract.State.Success).copy(
                    isPagingLoadingError = true
                )
            }
        } else {
            _uiState.update {
                (it as ProfileContract.State.Failure).copy(
                    isPagingLoadingError = true
                )
            }
        }
    }

    private fun onCloseFieldClick() {
        _uiState.update {
            ProfileContract.State.Success(
                profileModel = (it as ProfileContract.State.Failure).profileModel
                    ?: throw Throwable(),
                photoItemModels = it.photoItemModels ?: throw Throwable(),
                isPagingLoadingError = it.isPagingLoadingError,
                isExitEnabled = it.isExitEnabled
            )
        }
    }

    private fun onPagingCloseFieldClick() {
        _uiState.update {
            (it as ProfileContract.State.Success).copy(
                isPagingLoadingError = false
            )
        }
        _uiState.update {
            (it as ProfileContract.State.Success).copy(
                isPagingLoadingError = null
            )
        }
    }

    private fun onPhotoDetails(photoId: String) {
        _effect.update {
            ProfileContract.Effect.PhotoDetails(photoId)
        }
    }

    private fun onExitRequest() {
        if (_uiState.value is ProfileContract.State.Success) {
            _uiState.update {
                (it as ProfileContract.State.Success).copy(
                    isExitEnabled = true
                )
            }
        } else {
            _uiState.update {
                (it as ProfileContract.State.Failure).copy(
                    isExitEnabled = true
                )
            }
        }
    }

    private fun onExitCanceled() {
        if (_uiState.value is ProfileContract.State.Success) {
            _uiState.update {
                (it as ProfileContract.State.Success).copy(
                    isExitEnabled = false
                )
            }
        } else {
            _uiState.update {
                (it as ProfileContract.State.Failure).copy(
                    isExitEnabled = false
                )
            }
        }
    }

    private fun onExitConfirmed() {
        // clear all local data and logout
        _effect.update {
            ProfileContract.Effect.Exit
        }
    }


}