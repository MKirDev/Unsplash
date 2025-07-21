package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeSpanStyle
import com.mkirdev.unsplash.core.ui.theme.space_4

private const val TAG = "URL"
private const val START_LENGTH = 0

@Composable
fun HyperlinkText(
    downloadText: String,
    downloadLink: String,
    downloads: String,
    modifier: Modifier,
    textStyle: TextStyle,
    onDownloadClick: (String) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = TAG, annotation = downloadLink)
        withStyle(
            style = MaterialTheme.typography.bodyLargeSpanStyle.copy(
                color = MaterialTheme.colorScheme.onBackground,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(downloadText)
        }
        pop()
    }

    val interactionSource = remember { MutableInteractionSource() }

    val isDownloadsLarge by remember {
        derivedStateOf { downloads.length >= 6 }
    }

    val isTextEllipsisNeeded by remember {
        derivedStateOf { downloads.length >= 7 }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = annotatedString,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                annotatedString.getStringAnnotations(TAG, START_LENGTH, annotatedString.length)
                    .firstOrNull()?.let { annotation ->
                        onDownloadClick(annotation.item)
                    }
            },
            style = textStyle
        )
        Spacer(modifier = Modifier.width(space_4))
        Text(
            text = stringResource(id = R.string.downloads, downloads),
            color = MaterialTheme.colorScheme.onBackground,
            overflow = if (isTextEllipsisNeeded) TextOverflow.MiddleEllipsis else TextOverflow.Clip,
            maxLines = 1,
            style = textStyle
        )
        Spacer(modifier = Modifier.width(space_4))
        when (isDownloadsLarge) {
            true -> Unit
            false -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = stringResource(id = R.string.download_icon),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview
@Composable
private fun HyperlinkTextPreview() {
    UnsplashTheme(dynamicColor = false) {
        HyperlinkText(
            downloadText = stringResource(id = R.string.download),
            downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            downloads = "100",
            modifier = Modifier,
            textStyle = MaterialTheme.typography.bodyLarge,
            onDownloadClick = {}
        )
    }
}
