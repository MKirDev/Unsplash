package com.mkirdev.unsplash.profile.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.border_1
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_60
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoSmall
import com.mkirdev.unsplash.core.ui.widgets.TitleField
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
import com.mkirdev.unsplash.photo_item.feature.PhotoItem
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.profile.impl.ProfileTags
import com.mkirdev.unsplash.profile.models.ProfileModel
import com.mkirdev.unsplash.profile.preview.createPhotoItemModelsPreviewData
import com.mkirdev.unsplash.profile.preview.createProfileModelPreviewData
import kotlinx.coroutines.flow.Flow

@Composable
fun MainContentSuccess(
    modifier: Modifier,
    profileModel: ProfileModel,
    photoItemModels: Flow<PagingData<PhotoItemModel>>,
    isPagingLoadingError: Boolean?,
    onExitIconClick: () -> Unit,
    onPhotoItemClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onPagingCloseFieldClick: () -> Unit
) {
    Column(modifier = modifier) {
        TitleField(
            titleText = stringResource(id = R.string.profile),
            trailingIcon = R.drawable.ic_exit,
            modifier = Modifier.fillMaxWidth(),
            onTrailingClick = onExitIconClick
        )
        ProfileInfo(
            userImageUrl = profileModel.userImage,
            userFullName = profileModel.fullName,
            username = profileModel.username,
            bio = profileModel.bio,
            location = profileModel.location,
            email = profileModel.email,
            downloads = profileModel.downloads
        )
        profileModel.totalLikes?.let { totalLikes ->
            val pagedItems: LazyPagingItems<PhotoItemModel> =
                photoItemModels.collectAsLazyPagingItems()
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .testTag(ProfileTags.LAZY_COLUMN)
            ) {
                item(key = totalLikes) {
                    Column(Modifier.background(MaterialTheme.colorScheme.background)) {
                        HorizontalDivider(thickness = border_1, color = MaterialTheme.colorScheme.onBackground)
                        Text(
                            text = stringResource(id = R.string.count_of_liked_images, totalLikes),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        HorizontalDivider(thickness = border_1, color = MaterialTheme.colorScheme.onBackground)
                    }
                }
                items(
                    count = pagedItems.itemCount,
                    key = pagedItems.itemKey { photoItem -> photoItem.id }
                ) { index ->
                    val item = pagedItems[index]
                    item?.let {
                        PhotoItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onPhotoItemClick(it.id)
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
                                LikesInfoSmall(
                                    modifier = modifier.padding(
                                        end = padding_6,
                                        bottom = padding_10
                                    ),
                                    photoId = it.id,
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
                                    modifier = modifier.padding(
                                        end = padding_60,
                                        bottom = padding_10
                                    ),
                                    textStyle = MaterialTheme.typography.bodyLarge,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    onDownloadClick = onDownload
                                )
                            },
                            onLikeClick = onLikeClick,
                            onRemoveLikeClick = onRemoveLikeClick,
                            onDownloadClick = onDownloadClick
                        )
                    }
                }
                pagedItems.apply {
                    if (loadState.append is LoadState.Error) {
                        onLoadError()
                    }
                }
                when (isPagingLoadingError) {
                    true -> {
                        item {
                            ClosableErrorField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(padding_6)
                                    .testTag(ProfileTags.PAGING_ERROR_FIELD),
                                text = stringResource(id = R.string.collections_network_error),
                                textStyle = MaterialTheme.typography.bodyLargeMedium,
                                onClick = onPagingCloseFieldClick
                            )
                        }
                    }

                    else -> {}
                }

            }
        }
    }
}

@Preview
@Composable
private fun MainContentSuccessPreview() {
    UnsplashTheme(dynamicColor = false) {
        MainContentSuccess(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            profileModel = createProfileModelPreviewData(),
            photoItemModels = createPhotoItemModelsPreviewData(),
            isPagingLoadingError = true,
            onExitIconClick = { },
            onPhotoItemClick = {},
            onLikeClick = {},
            onRemoveLikeClick = {},
            onDownloadClick = {},
            onLoadError = { },
            onPagingCloseFieldClick = {}
        )
    }
}