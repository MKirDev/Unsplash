package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.rounded_corner_0

private const val EMPTY_STRING = ""
private const val ONE_LINE = 1

@Composable
fun SearchField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {

    var value by remember(text) {
        mutableStateOf(text)
    }

    var isEnabled by remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        modifier = modifier,
        enabled = isEnabled,
        textStyle = MaterialTheme.typography.headlineMedium,
        prefix = {
            if (!isEnabled) {
                Text(
                    text = stringResource(id = R.string.photo_feed).uppercase(),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        },
        trailingIcon = {
            TrailingIcon(isEnabled = isEnabled) {
                value = EMPTY_STRING
                isEnabled = !isEnabled
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        maxLines = ONE_LINE,
        shape = RoundedCornerShape(rounded_corner_0),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = Color.Transparent
        )
    )
}

@Composable
private fun TrailingIcon(isEnabled: Boolean, onClick: () -> Unit) {
    if (isEnabled) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                contentDescription = stringResource(id = R.string.close)
            )
        }
    } else {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = stringResource(
                    id = R.string.search
                ),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun SearchFieldPreview() {
    UnsplashTheme(dynamicColor = false) {
        Column(Modifier.fillMaxSize()) {
            SearchField(text = EMPTY_STRING, onValueChange = {}, modifier = Modifier.fillMaxWidth())
        }
    }
}
