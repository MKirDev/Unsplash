package com.mkirdev.unsplash.collections.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mkirdev.unsplash.collections.preview.createCollectionsPreviewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CollectionsViewModel : ViewModel(), CollectionsContract {

    private val _uiState =
        MutableStateFlow<CollectionsContract.State>(CollectionsContract.State.Idle)
    override val uiState: StateFlow<CollectionsContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<CollectionsContract.Effect?>(null)
    override val effect: StateFlow<CollectionsContract.Effect?> = _effect.asStateFlow()

    init {
        try {
            // load collections
            _uiState.update {
                CollectionsContract.State.Success(
                    collectionItemsModel = createCollectionsPreviewData().cachedIn(viewModelScope),
                    isPagingLoadingError = null
                )
            }
        } catch (t: Throwable) {
            _uiState.update {
                CollectionsContract.State.Failure(
                    error = t.message.toString()
                )
            }
        }

    }

    override fun handleEvent(event: CollectionsContract.Event) {
        when (event) {
            is CollectionsContract.Event.CollectionDetailsOpenedEvent -> onCollection(event.collectionId)
            CollectionsContract.Event.LoadingErrorEvent -> onErrorLoad()
            CollectionsContract.Event.FieldClosedEvent -> onCloseFieldClick()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onCollection(collectionId: String) {
        _effect.update {
            CollectionsContract.Effect.CollectionDetails(collectionId)
        }
    }

    private fun onErrorLoad() {
        _uiState.update {
            (it as CollectionsContract.State.Success).copy(
                isPagingLoadingError = true
            )
        }
    }

    private fun onCloseFieldClick() {
        if (_uiState.value is CollectionsContract.State.Success) {
            _uiState.update {
                (it as CollectionsContract.State.Success).copy(
                    isPagingLoadingError = false
                )
            }
            _uiState.update {
                (it as CollectionsContract.State.Success).copy(
                    isPagingLoadingError = null
                )
            }
        } else {
            _uiState.update {
                CollectionsContract.State.Success(
                    collectionItemsModel = MutableStateFlow(PagingData.empty()),
                    isPagingLoadingError = false
                )
            }
        }
    }

}