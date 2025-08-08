package com.mkirdev.unsplash.photo_item.feature

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.padding_4
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_60
import com.mkirdev.unsplash.core.ui.theme.space_10
import com.mkirdev.unsplash.core.ui.theme.space_4
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoSmall
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.preview.createPhotoItemPreviewData
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


@Composable
fun PhotoItem(
    modifier: Modifier,
    contentScale: ContentScale,
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

    val context = LocalContext.current

    val imageLoader = remember {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .allowHardware(false)
            .crossfade(false)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    LaunchedEffect(photoItemModel.imageUrl) {
        imageLoader.enqueue(
            ImageRequest.Builder(context)
                .data(photoItemModel.imageUrl)
                .build()
        )
    }

    Box(modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(photoItemModel.imageUrl)
                .build(),
            imageLoader = imageLoader,
            contentDescription = stringResource(id = R.string.photo_item),
            contentScale = contentScale,
            modifier = modifier.matchParentSize(),
            filterQuality = FilterQuality.None
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
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.spacedBy(space_10)
        ) {
            downloadText?.let {
                onDownloadClick?.let { onDownloadClick ->
                    it(
                        Modifier
                            .testTag(PhotoItemTags.DOWNLOAD_TEXT),
                        onDownloadClick
                    )
                }
            }
            likesInfo(
                Modifier
                    .testTag(PhotoItemTags.BUTTON),
                onLikeClick,
                onRemoveLikeClick
            )
        }
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
            contentScale = ContentScale.FillBounds,
            photoItemModel = photoItemModel,
            userImage = {
                UserImageSmall(imageUrl = photoItemModel.user.userImage)
            },
            userInfo = {
                UserInfoSmall(
                    name = photoItemModel.user.name,
                    userName = photoItemModel.user.username
                )
            },
            likesInfo = { modifier, onLike, onRemoveLike ->
                LikesInfoSmall(
                    modifier = modifier.padding(end = padding_6, bottom = padding_6),
                    photoId = photoItemModel.photoId,
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
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    onDownloadClick = onDownload
                )
            },
            onLikeClick = {},
            onRemoveLikeClick = {},
            onDownloadClick = {}
        )
    }
}