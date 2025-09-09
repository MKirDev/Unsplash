package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.icon_size_18
import com.mkirdev.unsplash.core.ui.theme.rounded_corner_0


private const val EMPTY_STRING = ""
private const val ONE_LINE = 1

@Composable
fun DisabledSearchField(
    modifier: Modifier,
    onTrailingIconClick: () -> Unit,
) {

    TextField(
        value = EMPTY_STRING,
        onValueChange = {},
        modifier = modifier,
        enabled = false,
        textStyle = MaterialTheme.typography.headlineMedium,
        prefix = {
            Text(
                text = stringResource(id = R.string.photo_feed).uppercase(),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        trailingIcon = {
            TrailingIcon {
                onTrailingIconClick()
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        maxLines = ONE_LINE,
        shape = RoundedCornerShape(rounded_corner_0),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun TrailingIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = stringResource(
                id = R.string.search
            ),
            modifier = Modifier.size(icon_size_18),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun DisabledSearchFieldPreview() {
    UnsplashTheme(dynamicColor = false) {
        Column(Modifier.fillMaxSize()) {
            DisabledSearchField(
                modifier = Modifier.fillMaxWidth(),
                onTrailingIconClick = {}
            )
        }
    }
}
