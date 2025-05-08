package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme

@Composable
fun ExifInfo(
    modifier: Modifier,
    make: String,
    model: String,
    exposureTime: String,
    aperture: String,
    focalLength: String,
    iso: String,
    textStyle: TextStyle
) {
    Column(modifier) {
        Text(
            text = stringResource(id = R.string.made_with, make),
            color = MaterialTheme.colorScheme.onBackground,
            softWrap = true,
            style = textStyle
        )
        Text(
            text = stringResource(id = R.string.model, model),
            color = MaterialTheme.colorScheme.onBackground,
            softWrap = true,
            style = textStyle
        )
        Text(
            text = stringResource(id = R.string.exposure, exposureTime),
            color = MaterialTheme.colorScheme.onBackground,
            style = textStyle
        )
        Text(
            text = stringResource(id = R.string.aperture, aperture),
            color = MaterialTheme.colorScheme.onBackground,
            style = textStyle
        )
        Text(
            text = stringResource(id = R.string.focal_length, focalLength),
            color = MaterialTheme.colorScheme.onBackground,
            style = textStyle
        )
        Text(
            text = stringResource(id = R.string.iso, iso),
            color = MaterialTheme.colorScheme.onBackground,
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun ExifInfoPreview() {
    UnsplashTheme(dynamicColor = false) {
        ExifInfo(
            modifier = Modifier.wrapContentSize(),
            make = "Canon",
            model = "EOS Rebel SL3",
            exposureTime = "0.008s",
            aperture = "3.5",
            focalLength = "50",
            iso = "100",
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}