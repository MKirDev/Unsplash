package com.mkirdev.unsplash.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.details.impl.DetailsContract
import com.mkirdev.unsplash.details.impl.PhotoDetailsScreen
import com.mkirdev.unsplash.details.impl.PhotoDetailsTags
import com.mkirdev.unsplash.details.utils.stubs.ErrorStub
import com.mkirdev.unsplash.details.utils.stubs.PhotoDetailsStub
import com.mkirdev.unsplash.details.utils.stubs.UpdatedCountStub
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScreenTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun field_whenUiStateDownloadSuccess_showsInfo() {
        val photoDetailsStub = PhotoDetailsStub.create()
        composeTestRule.setContent {
            PhotoDetailsScreen(
                uiState = DetailsContract.State.DownloadSuccess(
                    detailsModel = photoDetailsStub
                ),
                onShareClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLocationClick = {},
                onDownloadClick = {},
                onNavigateUp = {},
                onNavigateBack = {},
                onCloseFieldClick = {}
                )
        }

        composeTestRule.onNodeWithTag(PhotoDetailsTags.DOWNLOAD_INFO_FIELD).assertIsDisplayed()
    }

    @Test
    fun field_whenUiStateDownloadFailure_showsError() {
        val photoDetailsStub = PhotoDetailsStub.create()
        composeTestRule.setContent {
            PhotoDetailsScreen(
                uiState = DetailsContract.State.DownloadFailure(
                    detailsModel = photoDetailsStub
                ),
                onShareClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLocationClick = {},
                onDownloadClick = {},
                onNavigateUp = {},
                onNavigateBack = {},
                onCloseFieldClick = {}
            )
        }

        composeTestRule.onNodeWithTag(PhotoDetailsTags.DOWNLOAD_ERROR_FIELD).assertIsDisplayed()
    }

    @Test
    fun field_whenUiStateFailure_showsError() {
        val errorStub = ErrorStub.create()
        val photoDetailsStub = PhotoDetailsStub.create()
        val updatedCountStub = UpdatedCountStub.create()
        composeTestRule.setContent {
            PhotoDetailsScreen(
                uiState = DetailsContract.State.Failure(
                    error = errorStub,
                    detailsModel = photoDetailsStub,
                    updatedCount = updatedCountStub
                ),
                onShareClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLocationClick = {},
                onDownloadClick = {},
                onNavigateUp = {},
                onNavigateBack = {},
                onCloseFieldClick = {}
            )
        }

        composeTestRule.onNodeWithTag(PhotoDetailsTags.ERROR_FIELD).assertIsDisplayed()
    }

    @Test
    fun detailsScreen_whenUiStateSuccess_showsMainContent() {
        val photoDetailsStub = PhotoDetailsStub.create()
        composeTestRule.setContent {
            PhotoDetailsScreen(
                uiState = DetailsContract.State.Success(
                    detailsModel = photoDetailsStub,
                ),
                onShareClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLocationClick = {},
                onDownloadClick = {},
                onNavigateUp = {},
                onNavigateBack = {},
                onCloseFieldClick = {}
            )
        }

        composeTestRule.onNodeWithTag(PhotoDetailsTags.MAIN_CONTENT).assertIsDisplayed()
    }

    @Test
    fun detailsScreen_whenUiStateIdle_showsNothing() {
        composeTestRule.setContent {
            PhotoDetailsScreen(
                uiState = DetailsContract.State.Idle,
                onShareClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onLocationClick = {},
                onDownloadClick = {},
                onNavigateUp = {},
                onNavigateBack = {},
                onCloseFieldClick = {}
            )
        }

        composeTestRule.onNodeWithTag(PhotoDetailsTags.DOWNLOAD_INFO_FIELD).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(PhotoDetailsTags.DOWNLOAD_ERROR_FIELD).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(PhotoDetailsTags.ERROR_FIELD).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(PhotoDetailsTags.MAIN_CONTENT).assertIsNotDisplayed()
    }
}