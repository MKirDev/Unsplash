package com.mkirdev.unsplash.collection_item.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mkirdev.unsplash.collection_item.models.CollectionItemModel
import com.mkirdev.unsplash.collection_item.preview.createCollectionItemPreviewData
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.padding_16
import com.mkirdev.unsplash.core.ui.theme.padding_20
import com.mkirdev.unsplash.core.ui.theme.padding_4
import com.mkirdev.unsplash.core.ui.theme.space_4
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CollectionItem(
    modifier: Modifier,
    photoItemModel: CollectionItemModel,
    userImage: @Composable () -> Unit,
    userInfo: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        GlideImage(
            model = photoItemModel.coverPhotoUrl,
            contentDescription = stringResource(id = R.string.photo_item),
            contentScale = ContentScale.FillBounds
        )
        Column(Modifier.padding(start = padding_20, top = padding_16)) {
            Text(
                text = stringResource(
                    id = R.string.number_of_photo,
                    photoItemModel.totalPhotos
                ).uppercase(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = photoItemModel.title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displayLarge
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = padding_4, bottom = padding_4),
            horizontalArrangement = Arrangement.spacedBy(space_4)
        ) {
            userImage()
            userInfo()
        }
    }
}

@Preview
@Composable
private fun CollectionItemPreview() {
    val collectionItemModel = createCollectionItemPreviewData()
    UnsplashTheme(dynamicColor = false) {
        CollectionItem(
            modifier = Modifier.wrapContentWidth(),
            photoItemModel = collectionItemModel,
            userImage = {
                UserImageSmall(imageUrl = collectionItemModel.user.userImage)
            },
            userInfo = {
                UserInfoSmall(
                    name = collectionItemModel.user.name,
                    userName = collectionItemModel.user.username
                )
            }
        )
    }
}