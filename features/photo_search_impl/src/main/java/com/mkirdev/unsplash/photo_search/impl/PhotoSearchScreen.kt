package com.mkirdev.unsplash.photo_search.impl

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.item_width_64
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_2
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_70
import com.mkirdev.unsplash.core.ui.theme.space_6
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.EnabledSearchField
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoSmall
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.core.ui.widgets.StaticEmptyField
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
import com.mkirdev.unsplash.photo_item.feature.PhotoItem
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_search.models.ScrollState
import com.mkirdev.unsplash.photo_search.widgets.MainContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take


@Composable
internal fun PhotoSearchScreenWrapper(
    uiState: PhotoSearchContract.State,
    onSearch: (String) -> Unit,
    onPhotoClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onFeedClick: () -> Unit,
    onLoadError: () -> Unit,
    onSaveScrollState: (ScrollState) -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingCloseField: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit
) {

    val scrollState = when (uiState) {
        is PhotoSearchContract.State.Success -> uiState.scrollState
        is PhotoSearchContract.State.Failure -> uiState.scrollState
        is PhotoSearchContract.State.Loading -> uiState.scrollState
        is PhotoSearchContract.State.Idle -> uiState.scrollState
    }

    val models = when (uiState) {
        is PhotoSearchContract.State.Success -> uiState.models
        is PhotoSearchContract.State.Failure -> uiState.models
        else -> null
    }

    val searchText = when (uiState) {
        is PhotoSearchContract.State.Failure -> uiState.search
        is PhotoSearchContract.State.Success -> uiState.search
        is PhotoSearchContract.State.Loading -> uiState.search
        is PhotoSearchContract.State.Idle -> uiState.search
    }

    val errorText = when (uiState) {
        is PhotoSearchContract.State.Success -> null
        is PhotoSearchContract.State.Failure -> uiState.error
        else -> null
    }

    val isPagingError = when (uiState) {
        is PhotoSearchContract.State.Success -> uiState.isPagingLoadingError
        is PhotoSearchContract.State.Failure -> uiState.isPagingLoadingError
        else -> false
    }

    val gridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = scrollState.index,
        initialFirstVisibleItemScrollOffset = scrollState.offset
    )

    LaunchedEffect(gridState.isScrollInProgress) {
        if (!gridState.isScrollInProgress) {
            onSaveScrollState(
                ScrollState(
                    index = gridState.firstVisibleItemIndex,
                    offset = gridState.firstVisibleItemScrollOffset
                )
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            onSaveScrollState(
                ScrollState(
                    gridState.firstVisibleItemIndex,
                    gridState.firstVisibleItemScrollOffset
                )
            )
        }
    }

    PhotoSearchScreen(
        searchText = searchText,
        gridState = gridState,
        scrollIndex = scrollState.index,
        scrollOffset = scrollState.offset,
        models = models,
        onSearch = onSearch,
        onFeedClick = onFeedClick,
        onPhotoClick = { id ->
            onPhotoClick(id)
        },
        onLikeClick = onLikeClick,
        onRemoveLikeClick = onRemoveLikeClick,
        onLoadError = onLoadError,
        isPagingLoadingError = isPagingError,
        errorText = errorText,
        onCloseFieldClick = onCloseFieldClick,
        onPagingCloseField = onPagingCloseField,
        onPagingRetry = onPagingRetry
    )

}

@Composable
private fun PhotoSearchScreen(
    searchText: String,
    gridState: LazyGridState,
    scrollIndex: Int,
    scrollOffset: Int,
    models: Flow<PagingData<PhotoItemModel>>?,
    onSearch: (String) -> Unit,
    onFeedClick: () -> Unit,
    onPhotoClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onLoadError: () -> Unit,
    isPagingLoadingError: Boolean,
    errorText: String?,
    onCloseFieldClick: () -> Unit,
    onPagingCloseField: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit
) {

    Box(Modifier.fillMaxSize()) {
        Column {
            EnabledSearchField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = searchText,
                onValueChange = onSearch,
                onTrailingIconClick = onFeedClick
            )
            models?.let {
                val pagedItems = models.collectAsLazyPagingItems()

                LaunchedEffect(key1 = pagedItems.loadState, block = {
                    snapshotFlow { pagedItems.loadState.append }.collect { loadState ->
                        if (loadState is LoadState.Error) onLoadError()
                        else if (loadState is LoadState.Loading && isPagingLoadingError) onPagingCloseField()
                    }
                })

                LaunchedEffect(Unit) {
                    snapshotFlow { pagedItems.itemCount }
                        .filter { it > scrollIndex }
                        .take(1)
                        .collect {
                            gridState.scrollToItem(scrollIndex, scrollOffset)
                        }
                }

                MainContent(
                    gridState = gridState,
                    pagedItems = pagedItems,
                    errorText = errorText,
                    isPagingLoadingError = isPagingLoadingError,
                    onPhotoClick = onPhotoClick,
                    onLikeClick = onLikeClick,
                    onRemoveLikeClick = onRemoveLikeClick,
                    onCloseFieldClick = onCloseFieldClick,
                    onPagingRetry = onPagingRetry
                )
            }
        }
    }
}