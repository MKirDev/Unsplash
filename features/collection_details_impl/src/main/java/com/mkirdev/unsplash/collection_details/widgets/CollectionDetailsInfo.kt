package com.mkirdev.unsplash.collection_details.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mkirdev.unsplash.collection_details.models.CollectionDetailsModel
import com.mkirdev.unsplash.collection_details.preview.createCollectionDetailsPreviewData
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.item_height_138
import com.mkirdev.unsplash.core.ui.theme.padding_20
import com.mkirdev.unsplash.core.ui.theme.padding_4
import com.mkirdev.unsplash.core.ui.theme.space_16
import com.mkirdev.unsplash.core.ui.theme.space_24
import com.mkirdev.unsplash.core.ui.theme.space_4

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CollectionDetailsInfo(
    collectionDetailsModel: CollectionDetailsModel
) {
    Box {
        GlideImage(
            model = collectionDetailsModel.previewPhotoUrl,
            contentDescription = stringResource(id = R.string.preview_photo),
            modifier = Modifier
                .height(item_height_138)
                .background(MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth()
                .alpha(0.8f)
            ,
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(space_16))
            Text(
                text = collectionDetailsModel.title.uppercase(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(space_24))
            Text(
                text = collectionDetailsModel.description,
                modifier = Modifier.padding(horizontal = padding_20),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(space_4))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(horizontal = padding_4, vertical = padding_4),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(
                    id = R.string.count_of_images,
                    collectionDetailsModel.totalPhotos
                ),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(id = R.string.by_user, collectionDetailsModel.username),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun CollectionDetailsInfoPreview() {
    UnsplashTheme {
        CollectionDetailsInfo(
            collectionDetailsModel = createCollectionDetailsPreviewData()
        )
    }
}