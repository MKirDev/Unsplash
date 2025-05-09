package com.mkirdev.unsplash.details.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.icon_size_24
import com.mkirdev.unsplash.core.ui.theme.image_size_344
import com.mkirdev.unsplash.core.ui.theme.item_width_158
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_16
import com.mkirdev.unsplash.core.ui.theme.padding_2
import com.mkirdev.unsplash.core.ui.theme.padding_28
import com.mkirdev.unsplash.core.ui.theme.padding_4
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.space_10
import com.mkirdev.unsplash.core.ui.theme.space_40
import com.mkirdev.unsplash.core.ui.widgets.BioInfo
import com.mkirdev.unsplash.core.ui.widgets.ExifInfo
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfo
import com.mkirdev.unsplash.core.ui.widgets.TitleField
import com.mkirdev.unsplash.core.ui.widgets.UserImageMedium
import com.mkirdev.unsplash.core.ui.widgets.UserInfoMedium
import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.models.PhotoDetailsModel
import com.mkirdev.unsplash.details.preview.createPhotoDetailsPreview
import com.mkirdev.unsplash.photo_item.feature.PhotoItem

@Composable
fun MainContent(
    modifier: Modifier,
    photoDetailsModel: PhotoDetailsModel?,
    onShare: (String) -> Unit,
    onLike: (String) -> Unit,
    onRemoveLike: (String) -> Unit,
    onLocation: (CoordinatesModel) -> Unit,
    onDownload: (String) -> Unit,
    onNavigateUp: () -> Unit
) {

    Column(modifier) {
        TitleField(
            titleText = stringResource(id = R.string.photo_details),
            trailingIcon = R.drawable.ic_baseline_share_24,
            modifier = Modifier.fillMaxWidth(),
            onTrailingClick = {
                photoDetailsModel?.let {
                    onShare(photoDetailsModel.shareLink)
                }
            },
            onNavigateUp = onNavigateUp
        )
        Spacer(modifier = Modifier.height(space_40))
        photoDetailsModel?.let {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(padding_10)
            ) {
                PhotoItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(image_size_344),
                    photoItemModel = photoDetailsModel.photoItemModel,
                    userImage = {
                        UserImageMedium(imageUrl = photoDetailsModel.photoItemModel.user.userImage)
                    },
                    userInfo = {
                        UserInfoMedium(
                            name = photoDetailsModel.photoItemModel.user.name,
                            userName = photoDetailsModel.photoItemModel.user.userName
                        )
                    },
                    likesInfo = { modifier, onLike, onRemoveLike ->
                        LikesInfo(
                            modifier = modifier.padding(end = padding_6, bottom = padding_10),
                            photoId = photoDetailsModel.photoItemModel.id,
                            likes = photoDetailsModel.photoItemModel.likes,
                            isLikedPhoto = photoDetailsModel.photoItemModel.isLiked,
                            onLike = onLike,
                            onRemoveLike = onRemoveLike
                        )
                    },
                    onLikeClick = onLike,
                    onRemoveLikeClick = onRemoveLike
                )
                Spacer(modifier = Modifier.height(space_10))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        photoDetailsModel.location.coordinatesModel?.let {
                            IconButton(
                                onClick = {
                                    onLocation(photoDetailsModel.location.coordinatesModel)
                                },
                                modifier = Modifier.size(icon_size_24)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_location),
                                    contentDescription = stringResource(id = R.string.location),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        Text(
                            text = photoDetailsModel.location.place,
                            modifier = Modifier.padding(start = padding_4, top = padding_4),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HyperlinkText(
                        downloadText = stringResource(id = R.string.download),
                        downloadLink = photoDetailsModel.photoItemModel.downloadLink,
                        downloads = photoDetailsModel.photoItemModel.downloads,
                        modifier = Modifier.padding(top = padding_2),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        onDownload = onDownload
                    )
                }
                Text(
                    text = photoDetailsModel.tags,
                    modifier = Modifier.padding(
                        start = padding_28,
                        top = padding_10,
                        bottom = padding_16
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ExifInfo(
                        modifier = Modifier.width(item_width_158),
                        make = photoDetailsModel.exif.make,
                        model = photoDetailsModel.exif.model,
                        exposureTime = photoDetailsModel.exif.exposureTime,
                        aperture = photoDetailsModel.exif.aperture,
                        focalLength = photoDetailsModel.exif.focalLength,
                        iso = photoDetailsModel.exif.iso,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                    BioInfo(
                        modifier = Modifier.width(item_width_158),
                        userName = photoDetailsModel.photoItemModel.user.userName,
                        bio = photoDetailsModel.bio,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainContentPreview() {
    UnsplashTheme(dynamicColor = false) {
        MainContent(
            modifier = Modifier,
            photoDetailsModel = createPhotoDetailsPreview(),
            onShare = {},
            onLike = {},
            onRemoveLike = {},
            onLocation = {},
            onDownload = {},
            onNavigateUp = {}
        )
    }
}