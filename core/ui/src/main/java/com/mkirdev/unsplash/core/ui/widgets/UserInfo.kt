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
fun UserInfo(
    name: String,
    nameStyle: TextStyle,
    userName: String,
    userNameStyle: TextStyle
) {
    Column(verticalArrangement = Arrangement.spacedBy(space = space_1)) {
        Text(text = name, color = MaterialTheme.colorScheme.onPrimary, style = nameStyle)
        Text(
            text = stringResource(id = R.string.username_format, userName),
            color = MaterialTheme.colorScheme.onPrimary,
            style = userNameStyle
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ImageInfoPreview() {
    UnsplashTheme(dynamicColor = false) {
        UserInfo(
            name = "SumUp",
            nameStyle = MaterialTheme.typography.labelMedium,
            userName = "sumup",
            userNameStyle = MaterialTheme.typography.labelSmall
        )
    }
}