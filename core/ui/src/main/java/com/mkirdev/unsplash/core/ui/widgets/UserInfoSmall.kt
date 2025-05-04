package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.space_1


@Composable
fun UserInfoSmall(
    name: String,
    userName: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(space = space_1)) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = stringResource(id = R.string.username_format, userName),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ImageInfoSmallPreview() {
    UnsplashTheme(dynamicColor = false) {
        UserInfoSmall(
            name = "SumUp",
            userName = "sumup",
        )
    }
}