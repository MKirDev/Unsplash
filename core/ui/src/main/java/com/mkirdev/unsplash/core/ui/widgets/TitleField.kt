package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.rounded_corner_0

private const val EMPTY_STRING = ""

@Composable
fun TitleField(
    titleText: String,
    trailingIcon: Int? = null,
    modifier: Modifier,
    onNavigateUp: (() -> Unit)? = null,
    onTrailingClick: (() -> Unit)? = null,
) {
    Box {
        val isBackIconEnabled by remember {
            derivedStateOf {
                onNavigateUp != null
            }
        }
        when (isBackIconEnabled) {
            true -> {
                TextField(
                    value = EMPTY_STRING,
                    onValueChange = {},
                    modifier = modifier,
                    enabled = false,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    leadingIcon = {
                        onNavigateUp?.let {
                            IconButton(onClick = it) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_keyboard_backspace_24),
                                    contentDescription = stringResource(id = R.string.close),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    trailingIcon = {
                        trailingIcon?.let {
                            onTrailingClick?.let {
                                IconButton(onClick = onTrailingClick) {
                                    Icon(
                                        painter = painterResource(id = trailingIcon),
                                        contentDescription = stringResource(id = R.string.trailing_icon),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    },
                    prefix = {
                        Text(
                            text = titleText.uppercase(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    shape = RoundedCornerShape(rounded_corner_0),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            false -> {
                TextField(
                    value = EMPTY_STRING,
                    onValueChange = {},
                    modifier = modifier,
                    enabled = false,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    trailingIcon = {
                        trailingIcon?.let {
                            onTrailingClick?.let {
                                IconButton(onClick = onTrailingClick) {
                                    Icon(
                                        painter = painterResource(id = trailingIcon),
                                        contentDescription = stringResource(id = R.string.trailing_icon),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    },
                    prefix = {
                        Text(
                            text = titleText.uppercase(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    shape = RoundedCornerShape(rounded_corner_0),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun TitleFieldPreview() {
    UnsplashTheme(dynamicColor = false) {
        TitleField(
            titleText = stringResource(id = R.string.details),
            trailingIcon = R.drawable.ic_baseline_share_24,
            modifier = Modifier.fillMaxWidth(),
            onTrailingClick = {},
            onNavigateUp = {}
        )
    }
}