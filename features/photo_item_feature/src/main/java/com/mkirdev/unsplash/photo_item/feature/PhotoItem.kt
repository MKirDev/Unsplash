package com.mkirdev.unsplash.photo_item.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
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
import kotlin.math.roundToInt

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoItem(
    modifier: Modifier,
    photoItemModel: PhotoItemModel,
    userImage: @Composable () -> Unit,
    userInfo: @Composable () -> Unit,
    likesInfo: @Composable (
        Modifier,
        onLikeClick: (String) -> Unit,
        onRemoveLikeClick: (String) -> Unit
    ) -> Unit,
    downloadText: (@Composable (Modifier, onDownloadClick: (String) -> Unit) -> Unit)? = null,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: ((String) -> Unit)? = null
) {

    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val density = LocalDensity.current
    val screenWidthPx = with(density) {
        screenWidthDp.toPx().roundToInt()
    }
    val widthDp = with(density) { photoItemModel.width.toDp() }
    val heightDp = with(density) { photoItemModel.height.toDp() }

    Box(
        modifier = modifier.width(widthDp).height(heightDp)
    ) {
        GlideImage(
            model = photoItemModel.imageUrl,
            contentDescription = stringResource(id = R.string.photo_item),
            contentScale = ContentScale.FillBounds,
            requestBuilderTransform = {
                it
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .downsample(DownsampleStrategy.CENTER_INSIDE)
                    .override(screenWidthPx, (screenWidthPx / photoItemModel.aspectRatioImage).roundToInt())
            }
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
            onDownloadClick?.let { onDownloadClick ->
                it(
                    Modifier
                        .wrapContentWidth()
                        .align(Alignment.BottomEnd)
                        .testTag(PhotoItemTags.DOWNLOAD_TEXT),
                    onDownloadClick
                )
            }
        }
        likesInfo(
            Modifier
                .align(Alignment.BottomEnd)
                .testTag(PhotoItemTags.BUTTON),
            onLikeClick,
            onRemoveLikeClick
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
                    userName = photoItemModel.user.username
                )
            },
            likesInfo = { modifier, onLike, onRemoveLike ->
                LikesInfo(
                    modifier = modifier.padding(end = padding_6, bottom = padding_6),
                    photoId = photoItemModel.id,
                    likes = photoItemModel.likes,
                    isLikedPhoto = photoItemModel.isLiked,
                    onRemoveLikeClick = onRemoveLike,
                    onLikeClick = onLike
                )
            },
            downloadText = { modifier, onDownload ->
                HyperlinkText(
                    downloadText = stringResource(id = R.string.download),
                    downloadLink = photoItemModel.downloadLink,
                    downloads = photoItemModel.downloads,
                    modifier = modifier.padding(end = padding_60, bottom = padding_6),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    onDownloadClick = onDownload
                )
            },
            onLikeClick = {},
            onRemoveLikeClick = {},
            onDownloadClick = {}
        )
    }
}