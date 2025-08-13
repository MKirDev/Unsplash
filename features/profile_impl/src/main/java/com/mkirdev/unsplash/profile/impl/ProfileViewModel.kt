package com.mkirdev.unsplash.profile.impl

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.mkirdev.unsplash.domain.usecases.auth.ClearAuthTokensUseCase
import com.mkirdev.unsplash.domain.usecases.collections.ClearCollectionsDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosStorageUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.SaveScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.user.ClearUserDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.user.GetLikedPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.user.GetCurrentUserUseCase
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.profile.mappers.toPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val UPDATED_COUNT = 0
private const val LIKED = true
private const val UNLIKED = false

@Stable
internal class ProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getLikedPhotosUseCase: GetLikedPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
    private val addDownloadLinkUseCase: AddDownloadLinkUseCase,
    private val clearAuthTokensUseCase: ClearAuthTokensUseCase,
    private val clearPhotosStorageUseCase: ClearPhotosStorageUseCase,
    private val clearPhotosDatabaseUseCase: ClearPhotosDatabaseUseCase,
    private val clearCollectionsDatabaseUseCase: ClearCollectionsDatabaseUseCase,
    private val clearUserDatabaseUseCase: ClearUserDatabaseUseCase,
    private val saveScheduleFlagUseCase: SaveScheduleFlagUseCase
) : ViewModel(), ProfileContract {

    private val _uiState = MutableStateFlow<ProfileContract.State>(ProfileContract.State.Idle)

    @Stable
    override val uiState: StateFlow<ProfileContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<ProfileContract.Effect?>(null)

    @Stable
    override val effect: StateFlow<ProfileContract.Effect?> = _effect.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadData()
    }

    override fun handleEvent(event: ProfileContract.Event) {
        when (event) {
            is ProfileContract.Event.DownloadRequestedEvent -> onDownloadClick(event.link)
            is ProfileContract.Event.PhotoLikedEvent -> onLikeClick(event.photoId, LIKED)
            is ProfileContract.Event.PhotoUnlikedEvent -> onLikeClick(event.photoId, UNLIKED)
            is ProfileContract.Event.PhotoDetailsOpenedEvent -> onPhotoDetails(event.photoId)
            is ProfileContract.Event.PagingRetryEvent -> onPagingRetry(event.pagedItems)
            is ProfileContract.Event.PagingRefreshEvent -> onPagingRefresh(event.pagedItems)
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

    private fun loadData() {
        loadJob?.cancel()

        loadJob = viewModelScope.launch {
            try {
                _uiState.update {
                    ProfileContract.State.Loading
                }
                val profileModel = getCurrentUserUseCase.execute().toPresentation()
                val photoItemModels = getLikedPhotosUseCase.execute(profileModel.username)
                    .map { pagingData -> pagingData.map { it.toPresentation() } }
                    .cachedIn(viewModelScope)

                _uiState.update {
                    ProfileContract.State.Success(
                        profileModel = profileModel,
                        photoItemModels = photoItemModels,
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
    }

    private fun onDownloadClick(link: String) {
        viewModelScope.launch {
            try {
                addDownloadLinkUseCase.execute(link)
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
    }

    private fun onLikeClick(photoId: String, isLiked: Boolean) {
        viewModelScope.launch {
            try {
                if (isLiked) {
                    likePhotoLocalUseCase.execute(photoId)
                } else {
                    unlikePhotoLocalUseCase.execute(photoId)
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
    }

    private fun onPagingCloseFieldClick() {
        _uiState.update { currentState ->
            when (currentState) {
                is ProfileContract.State.Failure ->
                    ProfileContract.State.Success(
                        profileModel = currentState.profileModel ?: throw Throwable(),
                        photoItemModels = currentState.photoItemModels ?: throw Throwable(),
                        isPagingLoadingError = false,
                        isExitEnabled = currentState.isExitEnabled
                    )

                is ProfileContract.State.Success -> currentState.copy(isPagingLoadingError = false)
                else -> currentState
            }
        }
    }

    private fun onPagingRetry(pagedItems: LazyPagingItems<PhotoItemModel>) {
        pagedItems.retry()
    }

    private fun onPagingRefresh(pagedItems: LazyPagingItems<PhotoItemModel>) {
        pagedItems.refresh()
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
        viewModelScope.launch {
            loadJob?.cancel()

            async {
                saveScheduleFlagUseCase.execute(false)
                clearAuthTokensUseCase.execute()
                clearPhotosStorageUseCase.execute()
                clearPhotosDatabaseUseCase.execute()
                clearCollectionsDatabaseUseCase.execute()
                clearUserDatabaseUseCase.execute()
            }.await()

            launch(Dispatchers.Main) {
                _uiState.update {
                    (it as ProfileContract.State.Success).copy(
                        isExitEnabled = false
                    )
                }
                _effect.update {
                    ProfileContract.Effect.Exit
                }
            }
        }
    }
}

internal class ProfileViewModelFactory @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getLikedPhotosUseCase: GetLikedPhotosUseCase,
    private val likePhotoLocalUseCase: LikePhotoLocalUseCase,
    private val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
    private val addDownloadLinkUseCase: AddDownloadLinkUseCase,
    private val saveScheduleFlagUseCase: SaveScheduleFlagUseCase,
    private val clearAuthTokensUseCase: ClearAuthTokensUseCase,
    private val clearPhotosStorageUseCase: ClearPhotosStorageUseCase,
    private val clearPhotosDatabaseUseCase: ClearPhotosDatabaseUseCase,
    private val clearCollectionsDatabaseUseCase: ClearCollectionsDatabaseUseCase,
    private val clearUserDatabaseUseCase: ClearUserDatabaseUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ProfileViewModel(
            getCurrentUserUseCase = getCurrentUserUseCase,
            getLikedPhotosUseCase = getLikedPhotosUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase,
            addDownloadLinkUseCase = addDownloadLinkUseCase,
            saveScheduleFlagUseCase = saveScheduleFlagUseCase,
            clearAuthTokensUseCase = clearAuthTokensUseCase,
            clearPhotosStorageUseCase = clearPhotosStorageUseCase,
            clearPhotosDatabaseUseCase = clearPhotosDatabaseUseCase,
            clearCollectionsDatabaseUseCase = clearCollectionsDatabaseUseCase,
            clearUserDatabaseUseCase = clearUserDatabaseUseCase
        ) as T
    }
}