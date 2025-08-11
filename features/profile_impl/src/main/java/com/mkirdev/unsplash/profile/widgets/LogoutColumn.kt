package com.mkirdev.unsplash.profile.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.padding_16
import com.mkirdev.unsplash.core.ui.theme.padding_30
import com.mkirdev.unsplash.core.ui.theme.space_10
import com.mkirdev.unsplash.core.ui.theme.space_24
import com.mkirdev.unsplash.core.ui.widgets.StandardButton
import com.mkirdev.unsplash.core.ui.widgets.StaticEmptyField

@Composable
fun LogoutColumn(
    modifier: Modifier,
    onCanceledClick: () -> Unit,
    onConfirmedClick: () -> Unit
) {
    Column(modifier = modifier) {
        Column(
            Modifier
                .weight(0.8f)
                .alpha(0f)
        ) {
            StaticEmptyField(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            )
        }
        Column(
            modifier = Modifier
                .weight(0.2f)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = padding_16, vertical = padding_30)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.logout_question),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(space_24))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space_10)
            ) {
                StandardButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(id = R.string.text_button_cancel),
                    textStyle = MaterialTheme.typography.labelLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        onCanceledClick()
                    }
                )
                StandardButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(id = R.string.text_button_confirm),
                    textStyle = MaterialTheme.typography.labelLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        onConfirmedClick()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun LogoutColumnPreview() {
    UnsplashTheme(dynamicColor = false) {
        LogoutColumn(
            modifier = Modifier,
            onCanceledClick = {},
            onConfirmedClick = {}
        )
    }
}