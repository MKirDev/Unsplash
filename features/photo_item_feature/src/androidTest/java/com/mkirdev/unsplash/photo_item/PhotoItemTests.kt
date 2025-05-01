package com.mkirdev.unsplash.photo_item

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.core.ui.R
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
                onLike = {},
                onRemoveLike = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoItemTags.BUTTON).performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.like)).assertExists()
    }

    @Test
    fun iconButton_whenUserUnlikePhoto_showsCorrectlyIcon() {
        val photoItemStub = PhotoItemStub.create(true)
        composeTestRule.setContent {
            PhotoItem(
                modifier = Modifier,
                photoItemModel = photoItemStub,
                onLike = {},
                onRemoveLike = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoItemTags.BUTTON).performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.unlike)).assertExists()
    }
}