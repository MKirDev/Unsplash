package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.item_height_54
import com.mkirdev.unsplash.core.ui.theme.space_10
import com.mkirdev.unsplash.core.ui.theme.space_16

@Composable
fun StaticInfoField(
    modifier: Modifier,
    text: String,
    textStyle: TextStyle
) {
    Row(
        modifier = modifier.background(color = MaterialTheme.colorScheme.secondary),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(space_16))
        Text(
            text = text.uppercase(),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 2,
            softWrap = true,
            style = textStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StaticInfoFieldPreview() {
    StaticInfoField(
        modifier = Modifier.fillMaxWidth().height(item_height_54), text = stringResource(id = R.string.successfully_auth),
        textStyle = TextStyle.Default
    )
}