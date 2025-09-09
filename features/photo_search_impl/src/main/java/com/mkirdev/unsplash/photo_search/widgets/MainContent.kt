package com.mkirdev.unsplash.photo_search.widgets

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
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
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoSmall
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.core.ui.widgets.StaticEmptyField
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
import com.mkirdev.unsplash.photo_item.feature.PhotoItem
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel



private const val FIXED_COUNT = 2
private const val REPEAT_TIMES = 4

private const val PORTRAIT_FACTOR = 0.847f

private const val LANDSCAPE_FACTOR = 0.648f

@Composable
fun MainContent(
    gridState: LazyGridState,
    pagedItems: LazyPagingItems<PhotoItemModel>,
    errorText: String?,
    isPagingLoadingError: Boolean,
    onPhotoClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit
) {

    val configuration = LocalConfiguration.current

    val isPortrait = remember(configuration) {
        configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    val offsetY = remember(isPortrait) {
        val screenHeight = configuration.screenHeightDp.dp
        if (isPortrait) screenHeight * PORTRAIT_FACTOR else screenHeight * LANDSCAPE_FACTOR
    }

    val contentScale = remember(configuration.orientation) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ContentScale.FillBounds
        } else {
            ContentScale.FillHeight
        }
    }

    if (!errorText.isNullOrEmpty()) {
        ClosableErrorField(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 0.dp, y = offsetY)
                .zIndex(1f)
                .padding(horizontal = padding_10),
            text = errorText,
            textStyle = MaterialTheme.typography.bodyLargeMedium,
            onClick = onCloseFieldClick
        )
    }

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
                    key = pagedItems.itemKey { it.position }) { index ->
                    pagedItems[index]?.let { photoItem ->
                        PhotoItem(
                            modifier = Modifier
                                .padding(vertical = padding_2)
                                .fillMaxWidth()
                                .height(350.dp)
                                .clickable { onPhotoClick(photoItem.photoId) },
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
                                    photoId = photoItem.photoId,
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