package com.mkirdev.unsplash.photo_feed

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedContract
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedScreenWrapper
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedTags
import com.mkirdev.unsplash.photo_feed.utils.stubs.PhotoFeedErrorStub
import com.mkirdev.unsplash.photo_feed.utils.stubs.PhotoFeedSearchStub
import com.mkirdev.unsplash.photo_feed.utils.stubs.PhotoFeedStub
import com.mkirdev.unsplash.photo_feed.utils.stubs.UpdatedCountStub
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoFeedScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun photoFeed_whenUiStateIsSuccess_showsPhotos() {
        val searchStub = PhotoFeedSearchStub.create()
        val modelsStub = PhotoFeedStub.create()
        composeTestRule.setContent {
            PhotoFeedScreenWrapper(
                uiState = PhotoFeedContract.State.Success(
                    search = searchStub,
                    models = modelsStub,
                    isPagingLoadingError = false
                ),
                onSearch = {},
                onPhotoClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseField = {},
                onPagingRetry = {}
            )
        }

        composeTestRule.onAllNodesWithTag(PhotoFeedTags.ITEM).apply {
            fetchSemanticsNodes().forEachIndexed { index, _ ->
                get(index).assertIsDisplayed()
            }
        }
    }

    @Test
    fun photoFeed_whenUiStateIsFailure_showsErrorField() {
        val searchStub = PhotoFeedSearchStub.create()
        val modelsStub = PhotoFeedStub.create()
        val errorStub = PhotoFeedErrorStub.create()
        val updatedCountStub = UpdatedCountStub.create()
        composeTestRule.setContent {
            PhotoFeedScreenWrapper(
                uiState = PhotoFeedContract.State.Failure(
                    search = searchStub,
                    models = modelsStub,
                    isPagingLoadingError = false,
                    error = errorStub,
                    updatedCount = updatedCountStub
                ),
                onSearch = {},
                onPhotoClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseField = {},
                onPagingRetry = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoFeedTags.ERROR_FIELD).assertIsDisplayed()
    }

    @Test
    fun photoFeed_whenUiStateIsIdle_showsNothing() {
        composeTestRule.setContent {
            PhotoFeedScreenWrapper(
                uiState = PhotoFeedContract.State.Idle,
                onSearch = {},
                onPhotoClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseField = {},
                onPagingRetry = {}
            )
        }
        composeTestRule.onAllNodesWithTag(PhotoFeedTags.ITEM).apply {
            fetchSemanticsNodes().forEachIndexed { index, _ ->
                get(index).assertIsNotDisplayed()
            }
        }
        composeTestRule.onNodeWithTag(PhotoFeedTags.ERROR_FIELD).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD).assertIsNotDisplayed()
    }

    @Test
    fun searchField_whenUserClickOnSearchingIcon_enabled_inUiStateIsFailure() {
        val searchStub = PhotoFeedSearchStub.create()
        val modelsStub = PhotoFeedStub.create()
        val errorStub = PhotoFeedErrorStub.create()
        val updatedCountStub = UpdatedCountStub.create()
        composeTestRule.setContent {
            PhotoFeedScreenWrapper(
                uiState = PhotoFeedContract.State.Failure(
                    search = searchStub,
                    models = modelsStub,
                    isPagingLoadingError = false,
                    error = errorStub,
                    updatedCount = updatedCountStub
                ),
                onSearch = {},
                onPhotoClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseField = {},
                onPagingRetry = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD)
            .onChildren()
            .filterToOne(hasClickAction())
            .performClick()

        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD).assertIsEnabled()
    }

    @Test
    fun searchField_whenUserClickOnClosableIcon_notEnabled_inUiStateIsFailure() {
        val searchStub = PhotoFeedSearchStub.create()
        val modelsStub = PhotoFeedStub.create()
        val errorStub = PhotoFeedErrorStub.create()
        val updatedCountStub = UpdatedCountStub.create()
        composeTestRule.setContent {
            PhotoFeedScreenWrapper(
                uiState = PhotoFeedContract.State.Failure(
                    search = searchStub,
                    models = modelsStub,
                    isPagingLoadingError = false,
                    error = errorStub,
                    updatedCount = updatedCountStub
                ),
                onSearch = {},
                onPhotoClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseField = {},
                onPagingRetry = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD)
            .onChildren()
            .filterToOne(hasContentDescription(composeTestRule.activity.getString(R.string.search)))
            .performClick()

        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD)
            .onChildren()
            .filterToOne(hasContentDescription(composeTestRule.activity.getString(R.string.close)))
            .performClick()

        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD).assertIsNotEnabled()
    }

    @Test
    fun searchField_whenUserClickOnSearchingIcon_enabled_inUiStateIsSuccess() {
        val searchStub = PhotoFeedSearchStub.create()
        val modelsStub = PhotoFeedStub.create()
        composeTestRule.setContent {
            PhotoFeedScreenWrapper(
                uiState = PhotoFeedContract.State.Success(
                    search = searchStub,
                    models = modelsStub,
                    isPagingLoadingError = false
                ),
                onSearch = {},
                onPhotoClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseField = {},
                onPagingRetry = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD)
            .onChildren()
            .filterToOne(hasClickAction())
            .performClick()

        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD).assertIsEnabled()
    }

    @Test
    fun searchField_whenUserClickOnClosableIcon_notEnabled_inUiStateIsSuccess() {
        val searchStub = PhotoFeedSearchStub.create()
        val modelsStub = PhotoFeedStub.create()
        composeTestRule.setContent {
            PhotoFeedScreenWrapper(
                uiState = PhotoFeedContract.State.Success(
                    search = searchStub,
                    models = modelsStub,
                    isPagingLoadingError = false
                ),
                onSearch = {},
                onPhotoClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseField = {},
                onPagingRetry = {}
            )
        }
        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD)
            .onChildren()
            .filterToOne(hasContentDescription(composeTestRule.activity.getString(R.string.search)))
            .performClick()

        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD)
            .onChildren()
            .filterToOne(hasContentDescription(composeTestRule.activity.getString(R.string.close)))
            .performClick()

        composeTestRule.onNodeWithTag(PhotoFeedTags.SEARCH_FIELD).assertIsNotEnabled()
    }

}