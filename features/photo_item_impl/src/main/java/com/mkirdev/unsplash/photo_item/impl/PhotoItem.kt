package com.mkirdev.unsplash.photo_item.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodySmallWithoutLineHeight
import com.mkirdev.unsplash.core.ui.theme.image_size_18
import com.mkirdev.unsplash.core.ui.theme.padding_4
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.red
import com.mkirdev.unsplash.core.ui.theme.space_4
import com.mkirdev.unsplash.core.ui.theme.white
import com.mkirdev.unsplash.core.ui.widgets.UserInfo
import com.mkirdev.unsplash.photo_item.api.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.preview.createPreviewData

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoItem(
    modifier: Modifier,
    photoItemModel: PhotoItemModel,
    onLike: (String) -> Unit,
    onRemoveLike: (String) -> Unit
) {

    var isLiked by remember {
        mutableStateOf(photoItemModel.isLiked)
    }

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
            GlideImage(
                model = photoItemModel.user.userImage,
                contentDescription = stringResource(id = R.string.user_image),
                modifier = Modifier.size(image_size_18)
            )
            UserInfo(
                name = photoItemModel.user.name,
                nameStyle = MaterialTheme.typography.labelMedium,
                userName = photoItemModel.user.userName,
                userNameStyle = MaterialTheme.typography.labelSmall
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = padding_6, bottom = padding_6),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space_4)
        ) {
            Text(
                text = photoItemModel.likes,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmallWithoutLineHeight
            )
            Icon(
                painter = painterResource(id = if (isLiked) R.drawable.like_enabled else R.drawable.like_unenabled),
                contentDescription = stringResource(
                    id = if (isLiked) R.string.like else R.string.unlike
                ),
                modifier = Modifier.clickable {
                    isLiked = if (isLiked) {
                        onRemoveLike(photoItemModel.id)
                        !isLiked
                    } else {
                        onLike(photoItemModel.id)
                        !isLiked
                    }
                }.testTag(PhotoItemTags.BUTTON),
                tint = if (isLiked) red else white
            )

        }
    }
}

object PhotoItemTags {
    const val BUTTON = "PhotoItemTags:BUTTON"
}


@Preview
@Composable
private fun PhotoItemPreview() {
    val photoItemModel = createPreviewData()
    UnsplashTheme(dynamicColor = false) {
        PhotoItem(
            modifier = Modifier.aspectRatio(2.5f),
            photoItemModel = photoItemModel,
            onLike = {},
            onRemoveLike = {}
        )
    }

}