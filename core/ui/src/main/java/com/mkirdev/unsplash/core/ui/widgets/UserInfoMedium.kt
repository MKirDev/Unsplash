package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.item_width_170
import com.mkirdev.unsplash.core.ui.theme.padding_2
import com.mkirdev.unsplash.core.ui.theme.space_1

@Composable
fun UserInfoMedium(
    name: String,
    userName: String
) {
    Column(
    modifier = Modifier.width(item_width_170).padding(bottom = padding_2),
    verticalArrangement = Arrangement.spacedBy(space = space_1)
    ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(id = R.string.username_format, userName),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun UserInfoMediumPreview() {
    UnsplashTheme(dynamicColor = false) {
        UserInfoMedium(
            name = "SumUp SumUpSumUp SumUpSumUpSumUpSumUpSumUpSumUp",
            userName = "sumup",
        )
    }
}