package com.mkirdev.unsplash.photo_item.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.padding_4
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_60
import com.mkirdev.unsplash.core.ui.theme.space_4
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfo
import com.mkirdev.unsplash.core.ui.widgets.UserImageMedium
import com.mkirdev.unsplash.core.ui.widgets.UserInfoMedium
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.preview.createPhotoItemPreviewData

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoItem(
    modifier: Modifier,
    photoItemModel: PhotoItemModel,
    userImage: @Composable () -> Unit,
    userInfo: @Composable () -> Unit,
    likesInfo: @Composable (
        Modifier,
        onLike: (String) -> Unit,
        onRemoveLike: (String) -> Unit
    ) -> Unit,
    downloadText: (@Composable (Modifier, onDownload: (String) -> Unit) -> Unit)? = null,
    onLike: (String) -> Unit,
    onRemoveLike: (String) -> Unit,
    onDownload: ((String) -> Unit)? = null
) {

    Box(
        modifier = modifier
    ) {
        GlideImage(
            model = photoItemModel.imageUrl,
            contentDescription = stringResource(id = R.string.photo_item),
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = padding_4, bottom = padding_4),
            horizontalArrangement = Arrangement.spacedBy(space_4)
        ) {
            userImage()
            userInfo()
        }
        downloadText?.let {
            onDownload?.let { onDownload ->
                it(
                    Modifier
                        .wrapContentWidth()
                        .align(Alignment.BottomEnd)
                        .testTag(PhotoItemTags.DOWNLOAD_TEXT),
                    onDownload
                )
            }
        }
        likesInfo(
            Modifier
                .align(Alignment.BottomEnd)
                .testTag(PhotoItemTags.BUTTON),
            onLike,
            onRemoveLike
        )
    }
}

internal object PhotoItemTags {
    const val BUTTON = "PhotoItemTags:BUTTON"
    const val DOWNLOAD_TEXT = "PhotoItemTags:DOWNLOAD_TEXT"
}


@Preview
@Composable
private fun PhotoItemPreview() {
    val photoItemModel = createPhotoItemPreviewData()
    UnsplashTheme(dynamicColor = false) {
        PhotoItem(
            modifier = Modifier.aspectRatio(2.5f),
            photoItemModel = photoItemModel,
            userImage = {
                UserImageMedium(imageUrl = photoItemModel.user.userImage)
            },
            userInfo = {
                UserInfoMedium(
                    name = photoItemModel.user.name,
                    userName = photoItemModel.user.userName
                )
            },
            likesInfo = { modifier, onLike, onRemoveLike ->
                LikesInfo(
                    modifier = modifier.padding(end = padding_6, bottom = padding_6),
                    photoId = photoItemModel.id,
                    likes = photoItemModel.likes,
                    isLikedPhoto = photoItemModel.isLiked,
                    onRemoveLike = onRemoveLike,
                    onLike = onLike
                )
            },
            downloadText = { modifier, onDownload ->
                HyperlinkText(
                    downloadText = stringResource(id = R.string.download),
                    downloadUrl = photoItemModel.downloadLink,
                    downloads = photoItemModel.downloads,
                    modifier = modifier.padding(end = padding_60, bottom = padding_6),
                    textStyle = MaterialTheme.typography.headlineMedium,
                    onDownload = onDownload
                )
            },
            onLike = {},
            onRemoveLike = {},
            onDownload = {}
        )
    }
}