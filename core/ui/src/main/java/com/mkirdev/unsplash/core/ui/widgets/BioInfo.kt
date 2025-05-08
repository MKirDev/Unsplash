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
fun BioInfo(
    modifier: Modifier,
    userName: String,
    bio: String,
    textStyle: TextStyle
) {
    Column(modifier) {
        Text(
            text = stringResource(id = R.string.about_user_name, userName),
            color = MaterialTheme.colorScheme.onBackground,
            softWrap = true,
            style = textStyle
        )
        Text(
            text = bio,
            color = MaterialTheme.colorScheme.onBackground,
            softWrap = true,
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun BioInfoPreview() {
    UnsplashTheme(dynamicColor = false) {
        BioInfo(
            modifier = Modifier.wrapContentSize(),
            userName = "@alexo",
            bio = "Dreamer, creator.",
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}