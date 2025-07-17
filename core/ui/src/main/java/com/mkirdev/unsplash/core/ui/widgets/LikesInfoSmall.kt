package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodySmallWithoutLineHeight
import com.mkirdev.unsplash.core.ui.theme.icon_size_24
import com.mkirdev.unsplash.core.ui.theme.item_size_18
import com.mkirdev.unsplash.core.ui.theme.item_size_20
import com.mkirdev.unsplash.core.ui.theme.red
import com.mkirdev.unsplash.core.ui.theme.space_4
import com.mkirdev.unsplash.core.ui.theme.white

@Composable
fun LikesInfoSmall(
    modifier: Modifier,
    photoId: String,
    likes: String,
    isLikedPhoto: Boolean,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit
) {

    var isLiked by remember {
        mutableStateOf(isLikedPhoto)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space_4)
    ) {
        Text(
            text = likes,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmallWithoutLineHeight
        )
        IconButton(
            onClick = {
                isLiked = if (isLiked) {
                    onRemoveLikeClick(photoId)
                    !isLiked
                } else {
                    onLikeClick(photoId)
                    !isLiked
                }
            },
            modifier = Modifier
                .size(item_size_18),
        ) {
            Icon(
                painter = painterResource(id = if (isLiked) R.drawable.ic_filled_favorite else R.drawable.ic_outline_favorite),
                contentDescription = stringResource(
                    id = if (isLiked) R.string.like else R.string.unlike
                ),
                tint = if (isLiked) red else white
            )
        }
    }
}

@Preview
@Composable
private fun LikesInfoSmallPreview() {
    UnsplashTheme(dynamicColor = false) {
        LikesInfoSmall(
            modifier = Modifier,
            photoId = "1415",
            likes = "3.4k",
            isLikedPhoto = false,
            onLikeClick = {},
            onRemoveLikeClick = {}
        )
    }
}