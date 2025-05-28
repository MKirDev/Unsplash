package com.mkirdev.unsplash.onboarding.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.border_1
import com.mkirdev.unsplash.core.ui.theme.item_height_44
import com.mkirdev.unsplash.core.ui.theme.item_width_158
import com.mkirdev.unsplash.core.ui.theme.mid_green
import com.mkirdev.unsplash.core.ui.theme.padding_30

@Composable
internal fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = padding_30),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.height(item_height_44).width(item_width_158),
            visible = pagerState.currentPage == 2
        ) {
            OutlinedButton(
                onClick = onClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = mid_green,
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
                border = BorderStroke(border_1, color = Color.Transparent)
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}