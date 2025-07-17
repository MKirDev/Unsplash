package com.mkirdev.unsplash.photo_feed.impl

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.item_width_64
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_2
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_70
import com.mkirdev.unsplash.core.ui.theme.space_6
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoSmall
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.core.ui.widgets.SearchField
import com.mkirdev.unsplash.core.ui.widgets.StaticEmptyField
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
import com.mkirdev.unsplash.photo_feed.preview.createPhotoFeedPreviewData
import com.mkirdev.unsplash.photo_item.feature.PhotoItem
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take

private const val EMPTY_STRING = ""
private const val FIXED_COUNT = 2
private const val REPEAT_TIMES = 4

@Composable
internal fun PhotoFeedScreenWrapper(
    uiState: PhotoFeedContract.State,
    onSearch: (String) -> Unit,
    onPhotoClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingCloseField: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit
) {

    var scrollIndex by rememberSaveable { mutableIntStateOf(0) }
    var scrollOffset by rememberSaveable { mutableIntStateOf(0) }


    val gridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = scrollIndex,
        initialFirstVisibleItemScrollOffset = scrollOffset
    )
    LaunchedEffect(gridState.isScrollInProgress) {
        if (!gridState.isScrollInProgress) {
          scrollIndex = gridState.firstVisibleItemIndex
          scrollOffset = gridState.firstVisibleItemScrollOffset
        }
    }

    val models = when (uiState) {
        is PhotoFeedContract.State.Success -> uiState.models
        is PhotoFeedContract.State.Failure -> uiState.models
        else -> null
    }

    val searchText = when (uiState) {
        is PhotoFeedContract.State.Success -> uiState.search
        is PhotoFeedContract.State.Failure -> uiState.search
        else -> EMPTY_STRING
    }

    val errorText = when (uiState) {
        is PhotoFeedContract.State.Success -> null
        is PhotoFeedContract.State.Failure -> uiState.error
        else -> null
    }


    val isPagingError = when (uiState) {
        is PhotoFeedContract.State.Success -> uiState.isPagingLoadingError
        is PhotoFeedContract.State.Failure -> uiState.isPagingLoadingError
        else -> false
    }

    models?.let { flowPagingData ->
        val pagedItems = flowPagingData.collectAsLazyPagingItems()
        PhotoFeedScreen(
            gridState = gridState,
            scrollIndex = scrollIndex,
            scrollOffset = scrollOffset,
            pagedItems = pagedItems,
            searchText = searchText,
            onSearch = onSearch,
            onPhotoClick = onPhotoClick,
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
}

@Composable
private fun PhotoFeedScreen(
    gridState: LazyGridState,
    scrollIndex: Int,
    scrollOffset: Int,
    pagedItems: LazyPagingItems<PhotoItemModel>,
    searchText: String,
    onSearch: (String) -> Unit,
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

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val offsetY = if (isPortrait) screenHeight * 0.847f else screenHeight * 0.648f

    val contentScale = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        ContentScale.FillWidth
    } else {
        ContentScale.FillHeight
    }

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

    if (!errorText.isNullOrEmpty()) {
        ClosableErrorField(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 0.dp, y = offsetY)
                .zIndex(1f)
                .padding(horizontal = padding_10)
                .testTag(PhotoFeedTags.ERROR_FIELD),
            text = errorText,
            textStyle = MaterialTheme.typography.bodyLargeMedium,
            onClick = onCloseFieldClick
        )
    }

    Box(Modifier.fillMaxSize()) {
        Column {
            SearchField(
                text = searchText,
                onValueChange = {
                    onSearch(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(PhotoFeedTags.SEARCH_FIELD)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(FIXED_COUNT),
                state = gridState,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(
                    start = padding_10,
                    end = padding_10,
                    bottom = padding_10,
                    top = padding_6
                ),
                horizontalArrangement = Arrangement.spacedBy(space_6)
            ) {
                when (pagedItems.loadState.refresh) {
                    LoadState.Loading -> {
                        repeat(REPEAT_TIMES) {
                            item(span = { GridItemSpan(FIXED_COUNT) }) {
                                StaticEmptyField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = padding_70)
                                )
                            }
                        }
                        item(span = { GridItemSpan(FIXED_COUNT) }) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingIndicator(
                                    modifier = Modifier
                                        .width(item_width_64)
                                        .padding(bottom = padding_6)
                                )
                            }
                        }
                        item(span = { GridItemSpan(FIXED_COUNT) }) {
                            StaticEmptyField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = padding_70)
                            )
                        }
                    }

                    else -> {
                        items(
                            count = pagedItems.itemCount,
                            key = pagedItems.itemKey { it.id }) { index ->
                            pagedItems[index]?.let { photoItem ->
                                PhotoItem(
                                    modifier = Modifier
                                        .padding(vertical = padding_2)
                                        .fillMaxWidth()
                                        .height(350.dp)
                                        .clickable { onPhotoClick(photoItem.id) },
                                    contentScale = contentScale,
                                    photoItemModel = photoItem,
                                    userImage = { UserImageSmall(imageUrl = photoItem.user.userImage) },
                                    userInfo = {
                                        UserInfoSmall(
                                            name = photoItem.user.name,
                                            userName = photoItem.user.username
                                        )
                                    },
                                    likesInfo = { modifier, onLike, onRemoveLike ->
                                        LikesInfoSmall(
                                            modifier = modifier.padding(
                                                end = padding_6,
                                                bottom = padding_10
                                            ),
                                            photoId = photoItem.id,
                                            likes = photoItem.likes,
                                            isLikedPhoto = photoItem.isLiked,
                                            onRemoveLikeClick = onRemoveLike,
                                            onLikeClick = onLike
                                        )
                                    },
                                    onLikeClick = onLikeClick,
                                    onRemoveLikeClick = onRemoveLikeClick
                                )
                            }
                        }

                        if (isPagingLoadingError) {
                            item(span = { GridItemSpan(FIXED_COUNT) }) {
                                ClosableErrorField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = padding_6),
                                    text = stringResource(id = R.string.collections_network_error),
                                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                                    onClick = {
                                        onPagingRetry(pagedItems)
                                    }
                                )
                            }
                        }

                        item(span = { GridItemSpan(FIXED_COUNT) }) {
                            StaticEmptyField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = padding_70)
                            )
                        }
                    }
                }
            }
        }
    }
}

object PhotoFeedTags {
    const val ITEM = "PhotoFeedTags:ITEM"
    const val ERROR_FIELD = "PhotoFeedTags:ERROR_FIELD"
    const val SEARCH_FIELD = "PhotoFeedTags:SEARCH_FIELD"
}

@Preview
@Composable
private fun PhotoFeedScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        PhotoFeedScreenWrapper(
            uiState = PhotoFeedContract.State.Failure(
            search = "",
            models = createPhotoFeedPreviewData(),
            isPagingLoadingError = true,
            error = "",
            updatedCount = 1
        ),
            onSearch = {},
            onPhotoClick = {},
            onLikeClick = {},
            onRemoveLikeClick = {},
            onLoadError = {},
            onCloseFieldClick = {},
            onPagingCloseField = {},
            onPagingRetry = {})
    }
}