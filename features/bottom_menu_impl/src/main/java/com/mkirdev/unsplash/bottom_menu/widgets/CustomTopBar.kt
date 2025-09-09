package com.mkirdev.unsplash.bottom_menu.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.mkirdev.unsplash.core.ui.theme.item_height_56

private const val INDEX = 0f

@Composable
fun CustomTopBar(
    modifier: Modifier
) {
    Spacer(
        modifier = modifier
            .height(item_height_56)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .zIndex(INDEX)
    )
}