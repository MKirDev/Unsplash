package com.mkirdev.unsplash.photo_feed.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.item_width_64
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_14
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.space_6
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.CustomOutlinedButton
import com.mkirdev.unsplash.core.ui.widgets.LikesInfo
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.core.ui.widgets.SearchField
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
import com.mkirdev.unsplash.photo_feed.preview.createPhotoFeedPreviewData
import com.mkirdev.unsplash.photo_item.feature.PhotoItem

private const val FIXED_COUNT = 2

@Composable
fun PhotoFeedScreen(
    uiState: PhotoFeedContract.State,
    onSearch: (String) -> Unit,
    onPhotoClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onLoadPhotosClick: () -> Unit,
    onCloseFieldClick: () -> Unit
) {
    when (uiState) {
        PhotoFeedContract.State.Idle -> {

        }

        PhotoFeedContract.State.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingIndicator(
                    modifier = Modifier
                        .width(item_width_64)
                        .testTag(PhotoFeedTags.LOADING_INDICATOR)
                )
            }
        }

        is PhotoFeedContract.State.Failure -> {
            Column {
                SearchField(
                    text = uiState.search,
                    onValueChange = onSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(PhotoFeedTags.SEARCH_FIELD)
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(FIXED_COUNT),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .align(Alignment.TopCenter),
                        contentPadding = PaddingValues(padding_10),
                        horizontalArrangement = Arrangement.spacedBy(space_6),
                        verticalItemSpacing = space_6
                    ) {
                        items(
                            items = createPhotoFeedPreviewData()
                        ) {
                            PhotoItem(
                                modifier = Modifier
                                    .aspectRatio(it.aspectRatioImage)
                                    .clickable {
                                        onPhotoClick(it.id)
                                    },
                                photoItemModel = it,
                                userImage = {
                                    UserImageSmall(imageUrl = it.user.userImage)
                                },
                                userInfo = {
                                    UserInfoSmall(
                                        name = it.user.name,
                                        userName = it.user.userName
                                    )
                                },
                                likesInfo = { modifier, onLike, onRemoveLike ->
                                    LikesInfo(
                                        modifier = modifier.padding(end = padding_6, bottom = padding_6),
                                        photoId = it.id,
                                        likes = it.likes,
                                        isLikedPhoto = it.isLiked,
                                        onRemoveLike = onRemoveLike,
                                        onLike = onLike
                                    )
                                },
                                onLike = onLikeClick,
                                onRemoveLike = onRemoveLikeClick
                            )
                        }

                        item(span = StaggeredGridItemSpan.FullLine) {
                            CustomOutlinedButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = padding_14)
                            ) {

                            }
                        }
                    }
                    ClosableErrorField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .testTag(PhotoFeedTags.ERROR_FIELD),
                        text = uiState.error,
                        textStyle = MaterialTheme.typography.bodyLargeMedium,
                        onClick = onCloseFieldClick
                    )
                }
            }
        }

        is PhotoFeedContract.State.Success -> {
            Column {
                SearchField(
                    text = uiState.search,
                    onValueChange = onSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(PhotoFeedTags.SEARCH_FIELD)
                )
                Box(Modifier.fillMaxSize()) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(FIXED_COUNT),
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        contentPadding = PaddingValues(padding_10),
                        horizontalArrangement = Arrangement.spacedBy(space_6),
                        verticalItemSpacing = space_6
                    ) {
                        items(
                            items = uiState.models
                        ) {
                            PhotoItem(
                                modifier = Modifier
                                    .aspectRatio(it.aspectRatioImage)
                                    .testTag(PhotoFeedTags.ITEM),
                                photoItemModel = it,
                                userImage = {
                                    UserImageSmall(imageUrl = it.user.userImage)
                                },
                                userInfo = {
                                    UserInfoSmall(
                                        name = it.user.name,
                                        userName = it.user.userName
                                    )
                                },
                                likesInfo = { modifier, onLike, onRemoveLike ->
                                    LikesInfo(
                                        modifier = modifier.padding(end = padding_6, bottom = padding_6),
                                        photoId = it.id,
                                        likes = it.likes,
                                        isLikedPhoto = it.isLiked,
                                        onRemoveLike = onRemoveLike,
                                        onLike = onLike
                                    )
                                },
                                onLike = onLikeClick,
                                onRemoveLike = onRemoveLikeClick
                            )
                        }

                        item(span = StaggeredGridItemSpan.FullLine) {
                            CustomOutlinedButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = padding_14),
                                onClick = onLoadPhotosClick
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
    const val LOADING_INDICATOR = "PhotoFeedTags:LOADING_INDICATOR"
}

@Preview
@Composable
private fun PhotoFeedScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        PhotoFeedScreen(
            uiState = PhotoFeedContract.State.Success(
                search = "",
                models = createPhotoFeedPreviewData()
            ),
            onSearch = {},
            onPhotoClick = {},
            onLikeClick = {},
            onRemoveLikeClick = {},
            onLoadPhotosClick = {},
            onCloseFieldClick = {}
        )
    }
}
