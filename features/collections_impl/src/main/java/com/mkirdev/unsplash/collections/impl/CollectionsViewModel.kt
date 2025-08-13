package com.mkirdev.unsplash.collections.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.mkirdev.unsplash.collection_item.models.CollectionItemModel
import com.mkirdev.unsplash.collections.mappers.toPresentation
import com.mkirdev.unsplash.domain.models.Collection
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CollectionsViewModel(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel(), CollectionsContract {

    private val _uiState =
        MutableStateFlow<CollectionsContract.State>(CollectionsContract.State.Idle)
    override val uiState: StateFlow<CollectionsContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<CollectionsContract.Effect?>(null)
    override val effect: StateFlow<CollectionsContract.Effect?> = _effect.asStateFlow()

    init {
        loadCollections()
    }

    override fun handleEvent(event: CollectionsContract.Event) {
        when (event) {
            is CollectionsContract.Event.CollectionDetailsOpenedEvent -> onCollection(event.collectionId)
            is CollectionsContract.Event.PagingRetryEvent -> onPagingRetry(event.pagedItems)
            CollectionsContract.Event.LoadingErrorEvent -> onErrorLoad()
            CollectionsContract.Event.FieldClosedEvent -> onCloseFieldClick()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun loadCollections() {
        viewModelScope.launch {
            try {
                val collections = transformPagingData(getCollectionsUseCase.execute())
                _uiState.update {
                    CollectionsContract.State.Success(
                        collectionItemsModel = collections,
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
    }

    private fun transformPagingData(flow: Flow<PagingData<Collection>>): Flow<PagingData<CollectionItemModel>> {
        return flow
            .map { pagingData -> pagingData.map { it.toPresentation() } }
            .cachedIn(viewModelScope)
    }

    private fun onPagingRetry(pagedItems: LazyPagingItems<CollectionItemModel>) {
        pagedItems.retry()
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

internal class CollectionsViewModelFactory(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return CollectionsViewModel(
            getCollectionsUseCase = getCollectionsUseCase
        ) as T
    }
}