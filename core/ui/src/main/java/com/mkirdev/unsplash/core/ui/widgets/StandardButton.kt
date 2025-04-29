package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme

@Composable
fun StandardButton(modifier: Modifier, text: String, textStyle: TextStyle, colors: ButtonColors, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        shape = ShapeDefaults.Small
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StandardButtonPreview() {
    UnsplashTheme(dynamicColor = false) {
        StandardButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.auth),
            textStyle = MaterialTheme.typography.labelLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {

        }
    }
}