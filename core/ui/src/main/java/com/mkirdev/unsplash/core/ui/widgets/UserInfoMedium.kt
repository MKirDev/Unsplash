package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.space_1

@Composable
fun UserInfoMedium(
    name: String,
    userName: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(space = space_1)) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(id = R.string.username_format, userName),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ImageInfoMediumPreview() {
    UnsplashTheme(dynamicColor = false) {
        UserInfoMedium(
            name = "SumUp",
            userName = "sumup",
        )
    }
}