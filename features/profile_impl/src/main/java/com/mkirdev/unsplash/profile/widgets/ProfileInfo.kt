package com.mkirdev.unsplash.profile.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.displayLargeBold
import com.mkirdev.unsplash.core.ui.theme.icon_size_70
import com.mkirdev.unsplash.core.ui.theme.padding_16
import com.mkirdev.unsplash.core.ui.theme.rounded_corner_100
import com.mkirdev.unsplash.core.ui.theme.space_10
import com.mkirdev.unsplash.core.ui.theme.space_16
import com.mkirdev.unsplash.core.ui.theme.space_4
import com.mkirdev.unsplash.core.ui.theme.space_6

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileInfo(
    userImageUrl: String,
    userFullName: String,
    username: String,
    bio: String?,
    location: String?,
    email: String?,
    downloads: String?
) {
    Row(
        Modifier
            .padding(padding_16)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GlideImage(
            model = userImageUrl,
            contentDescription = stringResource(id = R.string.user_image),
            modifier = Modifier
                .size(icon_size_70)
                .clip(RoundedCornerShape(rounded_corner_100)),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.width(space_16))
        Column {
            Text(
                text = userFullName,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayLargeBold
            )
            Text(
                text = username,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(space_10))
            bio?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            Spacer(Modifier.height(space_10))
            location?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = stringResource(id = R.string.location),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.width(space_4))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(Modifier.height(space_4))
            email?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_mail_outline_24),
                        contentDescription = stringResource(id = R.string.mail),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.width(space_4))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(Modifier.height(space_4))
            downloads?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_download),
                        contentDescription = stringResource(id = R.string.download_icon),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.width(space_6))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileInfoPreview() {
    UnsplashTheme(dynamicColor = false) {
        ProfileInfo(
            userImageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            userFullName = "Spenser Sembrat",
            username = "@spensersembrat",
            bio = "The user's bio",
            location = "Location",
            email = "Preview@email.com",
            downloads = "1000"
        )
    }
}