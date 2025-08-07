package com.mkirdev.unsplash.profile.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.border_1
import com.mkirdev.unsplash.core.ui.theme.item_height_20
import com.mkirdev.unsplash.core.ui.theme.item_height_348
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_60
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoMedium
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoSmall
import com.mkirdev.unsplash.core.ui.widgets.TitleField
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
import com.mkirdev.unsplash.photo_item.feature.PhotoItem
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.profile.impl.ProfileTags
import com.mkirdev.unsplash.profile.models.ProfileModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take

@Composable
fun MainContent(
    modifier: Modifier,
    listState: LazyListState,
    profileModel: ProfileModel?,
    pagedItems: LazyPagingItems<PhotoItemModel>,
    scrollIndex: Int,
    scrollOffset: Int,
    isPagingLoadingError: Boolean,
    isExitEnabled: Boolean,
    onPhotoItemClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onPagingCloseFieldClick: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit,
    onExitIconClick: () -> Unit,
    onCanceledLogoutClick: () -> Unit,
    onConfirmedLogoutClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        snapshotFlow { pagedItems.itemCount }
            .filter { it > scrollIndex }
            .take(1)
            .collect {
                listState.scrollToItem(scrollIndex, scrollOffset)
            }
    }

    LaunchedEffect(key1 = pagedItems.loadState, block = {
        snapshotFlow { pagedItems.loadState.append }.collect { loadState ->
            if (loadState is LoadState.Error) onLoadError()
            else if (loadState is LoadState.Loading && isPagingLoadingError) onPagingCloseFieldClick()
        }
    })

    Column(modifier = modifier) {
        TitleField(
            titleText = stringResource(id = R.string.profile),
            trailingIcon = R.drawable.ic_exit,
            modifier = Modifier.fillMaxWidth(),
            onTrailingClick = onExitIconClick
        )
        profileModel?.let { model ->
            ProfileInfo(
                userImageUrl = model.userImage,
                userFullName = model.fullName,
                username = model.username,
                bio = model.bio,
                location = model.location,
                email = model.email,
                downloads = model.downloads
            )
            Column(Modifier.background(MaterialTheme.colorScheme.background)) {
                HorizontalDivider(
                    thickness = border_1,
                    color = MaterialTheme.colorScheme.onBackground
                )
                model.totalLikes?.let { likes ->
                    Text(
                        text = stringResource(id = R.string.count_of_liked_images, likes),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    HorizontalDivider(
                        thickness = border_1,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .testTag(ProfileTags.LAZY_COLUMN)
        ) {
            items(
                count = pagedItems.itemCount,
                key = pagedItems.itemKey { photoItem -> photoItem.photoId }
            ) { index ->
                val item = pagedItems[index]
                item?.let {
                    PhotoItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(item_height_348)
                            .clickable {
                                onPhotoItemClick(it.photoId)
                            },
                        contentScale = ContentScale.FillBounds,
                        photoItemModel = it,
                        userImage = {
                            UserImageSmall(imageUrl = it.user.userImage)
                        },
                        userInfo = {
                            UserInfoSmall(
                                name = it.user.name,
                                userName = it.user.username
                            )
                        },
                        likesInfo = { modifier, onLike, onRemoveLike ->
                            LikesInfoMedium(
                                modifier = modifier.padding(
                                    end = padding_6,
                                    bottom = padding_10
                                ),
                                photoId = it.photoId,
                                likes = it.likes,
                                isLikedPhoto = it.isLiked,
                                onLikeClick = onLike,
                                onRemoveLikeClick = onRemoveLike
                            )
                        },
                        downloadText = { modifier, onDownload ->
                            HyperlinkText(
                                downloadText = stringResource(id = R.string.download),
                                downloadLink = it.downloadLink,
                                downloads = it.downloads,
                                modifier = modifier
                                    .padding(bottom = padding_10)
                                    .height(item_height_20),
                                textStyle = MaterialTheme.typography.bodyLarge,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSecondary,
                                onDownloadClick = onDownload
                            )
                        },
                        onLikeClick = onLikeClick,
                        onRemoveLikeClick = onRemoveLikeClick,
                        onDownloadClick = onDownloadClick
                    )
                }
            }
            when (isPagingLoadingError) {
                true -> {
                    item {
                        Box(Modifier.background(MaterialTheme.colorScheme.background)){
                            ClosableErrorField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(ProfileTags.PAGING_ERROR_FIELD),
                                text = stringResource(id = R.string.collections_network_error),
                                textStyle = MaterialTheme.typography.bodyLargeMedium,
                                onClick = { onPagingRetry(pagedItems) }
                            )
                        }
                    }
                }

                else -> {}
            }

        }
        when (isExitEnabled) {
            true -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(enabled = false) {}
                )
                LogoutColumn(
                    modifier = Modifier.testTag(ProfileTags.LOGOUT_COLUMN),
                    onCanceledClick = onCanceledLogoutClick,
                    onConfirmedClick = onConfirmedLogoutClick
                )

            }

            false -> {}
        }
    }
}