package com.mkirdev.unsplash.details.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import com.mkirdev.unsplash.core.ui.theme.item_width_158
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_16
import com.mkirdev.unsplash.core.ui.theme.padding_2
import com.mkirdev.unsplash.core.ui.theme.padding_24
import com.mkirdev.unsplash.core.ui.theme.padding_4
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.space_10
import com.mkirdev.unsplash.core.ui.theme.space_20
import com.mkirdev.unsplash.core.ui.widgets.BioInfo
import com.mkirdev.unsplash.core.ui.widgets.ExifInfo
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfo
import com.mkirdev.unsplash.core.ui.widgets.TitleField
import com.mkirdev.unsplash.core.ui.widgets.UserImageMedium
import com.mkirdev.unsplash.core.ui.widgets.UserInfoMedium
import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.models.DetailsModel
import com.mkirdev.unsplash.details.preview.createPhotoDetailsPreview
import com.mkirdev.unsplash.photo_item.feature.PhotoItem

@Composable
fun MainContent(
    modifier: Modifier,
    detailsModel: DetailsModel,
    onShareClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onLocationClick: (CoordinatesModel) -> Unit,
    onDownloadClick: (String) -> Unit,
    onNavigateUp: () -> Unit
) {

    Column(modifier) {
        TitleField(
            titleText = stringResource(id = R.string.photo_details),
            trailingIcon = R.drawable.ic_baseline_share_24,
            modifier = Modifier.fillMaxWidth(),
            onTrailingClick = {
                onShareClick(detailsModel.shareLink)
            },
            onNavigateUp = onNavigateUp
        )
        Spacer(modifier = Modifier.height(space_20))
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(padding_10)
        ) {
            PhotoItem(
                modifier = Modifier
                    .width(detailsModel.photoItemModel.width)
                    .aspectRatio(detailsModel.photoItemModel.aspectRatioImage)
                ,
                photoItemModel = detailsModel.photoItemModel,
                userImage = {
                    UserImageMedium(imageUrl = detailsModel.photoItemModel.user.userImage)
                },
                userInfo = {
                    UserInfoMedium(
                        name = detailsModel.photoItemModel.user.name,
                        userName = detailsModel.photoItemModel.user.username
                    )
                },
                likesInfo = { modifier, onLike, onRemoveLike ->
                    LikesInfo(
                        modifier = modifier.padding(end = padding_6, bottom = padding_10),
                        photoId = detailsModel.photoItemModel.id,
                        likes = detailsModel.photoItemModel.likes,
                        isLikedPhoto = detailsModel.photoItemModel.isLiked,
                        onLikeClick = onLike,
                        onRemoveLikeClick = onRemoveLike
                    )
                },
                onLikeClick = onLikeClick,
                onRemoveLikeClick = onRemoveLikeClick
            )
            Spacer(modifier = Modifier.height(space_10))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space_20)
            ) {
                Row(Modifier.weight(0.65f)) {
                    detailsModel.location?.let {
                        it.coordinatesModel?.let {
                            IconButton(
                                onClick = {
                                    onLocationClick(it)
                                },
                                modifier = Modifier.size(icon_size_24).padding(top = padding_4)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_location),
                                    contentDescription = stringResource(id = R.string.location),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        it.place?.let { it1 ->
                            Text(
                                text = it1,
                                modifier = Modifier.padding(start = padding_4, top = padding_4),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                HyperlinkText(
                    downloadText = stringResource(id = R.string.download),
                    downloadLink = detailsModel.photoItemModel.downloadLink,
                    downloads = detailsModel.photoItemModel.downloads,
                    modifier = Modifier.padding(top = padding_2).weight(0.35f),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    onDownloadClick = onDownloadClick
                )
            }
            detailsModel.tags?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(
                        start = padding_16,
                        top = padding_24,
                        bottom = padding_16
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = padding_10),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                detailsModel.exif?.let {
                    ExifInfo(
                        modifier = Modifier.width(item_width_158),
                        make = it.make,
                        model = it.model,
                        exposureTime = it.exposureTime,
                        aperture = it.aperture,
                        focalLength = it.focalLength,
                        iso = it.iso,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }
                detailsModel.bio?.let {
                    BioInfo(
                        modifier = Modifier.width(item_width_158),
                        userName = detailsModel.photoItemModel.user.username,
                        bio = it,
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
            detailsModel = createPhotoDetailsPreview(),
            onShareClick = {},
            onLikeClick = {},
            onRemoveLikeClick = {},
            onLocationClick = {},
            onDownloadClick = {},
            onNavigateUp = {}
        )
    }
}