package com.mkirdev.unsplash.upload_and_track.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.padding_320

@Composable
fun UploadAndTrackScreen(text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(top = padding_320),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.displayMedium
        )
    }

}


@Preview
@Composable
internal fun UploadAndTrackScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        UploadAndTrackScreen(stringResource(R.string.upload_and_track))
    }
}