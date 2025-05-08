package com.mkirdev.unsplash.photo_item

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_60
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfo
import com.mkirdev.unsplash.core.ui.widgets.UserImageMedium
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoMedium
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
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

    @Test
    fun iconButton_whenUserLikePhoto_showsCorrectlyIcon() {
        val photoItemStub = PhotoItemStub.create(false)
        composeTestRule.setContent {
            PhotoItem(
                modifier = Modifier,
                photoItemModel = photoItemStub,
                userImage = {
                    UserImageSmall(imageUrl = photoItemStub.user.userImage)
                },
                userInfo = {
                    UserInfoSmall(
                        name = photoItemStub.user.name,
                        userName = photoItemStub.user.userName
                    )
                },
                likesInfo = { modifier, onLike, onRemoveLike ->
                    LikesInfo(
                        modifier = modifier.padding(start = padding_6, bottom = padding_6),
                        photoId = photoItemStub.id,
                        likes = photoItemStub.likes,
                        isLikedPhoto = photoItemStub.isLiked,
                        onRemoveLike = onRemoveLike,
                        onLike = onLike
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

    @Test
    fun iconButton_whenUserUnlikePhoto_showsCorrectlyIcon() {
        val photoItemStub = PhotoItemStub.create(true)
        composeTestRule.setContent {
            PhotoItem(
                modifier = Modifier,
                photoItemModel = photoItemStub,
                userImage = {
                    UserImageSmall(imageUrl = photoItemStub.user.userImage)
                },
                userInfo = {
                    UserInfoSmall(
                        name = photoItemStub.user.name,
                        userName = photoItemStub.user.userName
                    )
                },
                likesInfo = { modifier, onLike, onRemoveLike ->
                    LikesInfo(
                        modifier = modifier.padding(start = padding_6, bottom = padding_6),
                        photoId = photoItemStub.id,
                        likes = photoItemStub.likes,
                        isLikedPhoto = photoItemStub.isLiked,
                        onRemoveLike = onRemoveLike,
                        onLike = onLike
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

    @Test
    fun downloadText_whenOnScreen_correctlyWorked() {
        val photoItemStub = PhotoItemStub.create(true)
        composeTestRule.setContent {
            PhotoItem(
                modifier = Modifier,
                photoItemModel = photoItemStub,
                userImage = {
                    UserImageMedium(imageUrl = photoItemStub.user.userImage)
                },
                userInfo = {
                    UserInfoMedium(
                        name = photoItemStub.user.name,
                        userName = photoItemStub.user.userName
                    )
                },
                likesInfo = { modifier, onLike, onRemoveLike ->
                    LikesInfo(
                        modifier = modifier.padding(start = padding_6, bottom = padding_6),
                        photoId = photoItemStub.id,
                        likes = photoItemStub.likes,
                        isLikedPhoto = photoItemStub.isLiked,
                        onRemoveLike = onRemoveLike,
                        onLike = onLike
                    )
                },
                downloadText = { modifier, onDownload ->
                    HyperlinkText(
                        downloadText = stringResource(id = R.string.download),
                        downloadLink = photoItemStub.downloadLink,
                        downloads = photoItemStub.downloads,
                        modifier = modifier.padding(end = padding_60, bottom = padding_10),
                        textStyle = MaterialTheme.typography.headlineMedium,
                        onDownload = onDownload
                    )
                },
                onLike = {},
                onRemoveLike = {},
                onDownload = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoItemTags.DOWNLOAD_TEXT).assertIsEnabled()
        composeTestRule.onNodeWithTag(PhotoItemTags.DOWNLOAD_TEXT).onChildren().filterToOne(
            hasClickAction()
        ).assertIsEnabled()
    }
}