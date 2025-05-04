package com.mkirdev.unsplash.photo_item

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.image_size_18
import com.mkirdev.unsplash.core.ui.widgets.UserInfo
import com.mkirdev.unsplash.photo_item.utils.stubs.PhotoItemStub
import com.mkirdev.unsplash.photo_item.feature.PhotoItem
import com.mkirdev.unsplash.photo_item.feature.PhotoItemTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class PhotoItemTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalGlideComposeApi::class)
    @Test
    fun iconButton_whenUserLikePhoto_showsCorrectlyIcon() {
        val photoItemStub = PhotoItemStub.create(false)
        composeTestRule.setContent {
            PhotoItem(
                modifier = Modifier,
                photoItemModel = photoItemStub,
                userImage = {
                    GlideImage(
                        model = photoItemStub.user.userImage,
                        contentDescription = stringResource(id = R.string.user_image),
                        modifier = Modifier.size(image_size_18)
                    )
                },
                userInfo = {
                    UserInfo(
                        name = photoItemStub.user.name,
                        nameStyle = MaterialTheme.typography.labelMedium,
                        userName = photoItemStub.user.userName,
                        userNameStyle = MaterialTheme.typography.labelSmall
                    )
                },
                onLike = {},
                onRemoveLike = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoItemTags.BUTTON).performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.like))
            .assertExists()
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Test
    fun iconButton_whenUserUnlikePhoto_showsCorrectlyIcon() {
        val photoItemStub = PhotoItemStub.create(true)
        composeTestRule.setContent {
            PhotoItem(
                modifier = Modifier,
                photoItemModel = photoItemStub,
                userImage = {
                    GlideImage(
                        model = photoItemStub.user.userImage,
                        contentDescription = stringResource(id = R.string.user_image),
                        modifier = Modifier.size(image_size_18)
                    )
                },
                userInfo = {
                    UserInfo(
                        name = photoItemStub.user.name,
                        nameStyle = MaterialTheme.typography.labelMedium,
                        userName = photoItemStub.user.userName,
                        userNameStyle = MaterialTheme.typography.labelSmall
                    )
                },
                onLike = {},
                onRemoveLike = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoItemTags.BUTTON).performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.unlike))
            .assertExists()
    }
}