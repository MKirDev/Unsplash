package com.mkirdev.unsplash.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.profile.impl.ProfileContract
import com.mkirdev.unsplash.profile.impl.ProfileScreen
import com.mkirdev.unsplash.profile.impl.ProfileTags
import com.mkirdev.unsplash.profile.utils.stubs.ErrorStub
import com.mkirdev.unsplash.profile.utils.stubs.ExitStub
import com.mkirdev.unsplash.profile.utils.stubs.PagingErrorStub
import com.mkirdev.unsplash.profile.utils.stubs.PhotoItemsStub
import com.mkirdev.unsplash.profile.utils.stubs.ProfileStub
import com.mkirdev.unsplash.profile.utils.stubs.UpdatedCountStub
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingIndicator_whenUiStateIsLoading_isDisplayed() {
        composeTestRule.setContent {
            ProfileScreen(
                uiState = ProfileContract.State.Loading,
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onExitIconClick = {},
                onCanceledLogoutClick = {},
                onConfirmedLogoutClick = {}
            )
        }

        composeTestRule.onNodeWithTag(ProfileTags.LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun field_whenUiStateIsFailure_showsError() {
        val errorStub = ErrorStub.create()
        val pagingErrorStub = PagingErrorStub.create(isError = false)
        val exitStub = ExitStub.create(isExitEnabled = false)
        val updatedCountStub = UpdatedCountStub.create()
        val profileStub = ProfileStub.create()
        val photoItemsStub = PhotoItemsStub.create()
        composeTestRule.setContent {
            ProfileScreen(
                uiState = ProfileContract.State.Failure(
                    error = errorStub,
                    isPagingLoadingError = pagingErrorStub,
                    isExitEnabled = exitStub,
                    updatedCount = updatedCountStub,
                    profileModel = profileStub,
                    photoItemModels = photoItemsStub
                ),
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onExitIconClick = {},
                onCanceledLogoutClick = {},
                onConfirmedLogoutClick = {}
            )
        }

        composeTestRule.onNodeWithTag(ProfileTags.ERROR_FIELD).assertIsDisplayed()
    }

    @Test
    fun paging_whenLoadStateIsAppendAndHasError_showsError() {
        val profileStub = ProfileStub.create()
        val photoItemsStub = PhotoItemsStub.create()
        val pagingErrorStub = PagingErrorStub.create(true)
        val exitStub = ExitStub.create(false)
        composeTestRule.setContent {
            ProfileScreen(
                uiState = ProfileContract.State.Success(
                    profileModel = profileStub,
                    photoItemModels = photoItemsStub,
                    isPagingLoadingError = pagingErrorStub,
                    isExitEnabled = exitStub
                ),
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onExitIconClick = {},
                onCanceledLogoutClick = {},
                onConfirmedLogoutClick = {}
            )
        }
        composeTestRule.onNodeWithTag(ProfileTags.LAZY_COLUMN)
            .performScrollToNode(hasTestTag(ProfileTags.PAGING_ERROR_FIELD)).assertIsDisplayed()
    }

    @Test
    fun fields_whenUiStateIsFailure_and_paging_whenLoadStateIsAppendAndHasError_showErrors() {
        val errorStub = ErrorStub.create()
        val pagingErrorStub = PagingErrorStub.create(isError = true)
        val exitStub = ExitStub.create(isExitEnabled = false)
        val updatedCountStub = UpdatedCountStub.create()
        val profileStub = ProfileStub.create()
        val photoItemsStub = PhotoItemsStub.create()
        composeTestRule.setContent {
            ProfileScreen(
                uiState = ProfileContract.State.Failure(
                    error = errorStub,
                    isPagingLoadingError = pagingErrorStub,
                    isExitEnabled = exitStub,
                    updatedCount = updatedCountStub,
                    profileModel = profileStub,
                    photoItemModels = photoItemsStub
                ),
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onExitIconClick = {},
                onCanceledLogoutClick = {},
                onConfirmedLogoutClick = {}
            )
        }

        composeTestRule.onNodeWithTag(ProfileTags.ERROR_FIELD).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ProfileTags.LAZY_COLUMN)
            .performScrollToNode(hasTestTag(ProfileTags.PAGING_ERROR_FIELD)).assertIsDisplayed()
    }

    @Test
    fun logoutColumn_whenUiStateIsFailure_and_propertyIsExitEnableIsTrue_showsCorrectly() {
        val errorStub = ErrorStub.create()
        val pagingErrorStub = PagingErrorStub.create(isError = true)
        val exitStub = ExitStub.create(isExitEnabled = true)
        val updatedCountStub = UpdatedCountStub.create()
        val profileStub = ProfileStub.create()
        val photoItemsStub = PhotoItemsStub.create()
        composeTestRule.setContent {
            ProfileScreen(
                uiState = ProfileContract.State.Failure(
                    error = errorStub,
                    isPagingLoadingError = pagingErrorStub,
                    isExitEnabled = exitStub,
                    updatedCount = updatedCountStub,
                    profileModel = profileStub,
                    photoItemModels = photoItemsStub
                ),
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onExitIconClick = {},
                onCanceledLogoutClick = {},
                onConfirmedLogoutClick = {}
            )
        }
        composeTestRule.onNodeWithTag(ProfileTags.LOGOUT_COLUMN).assertIsDisplayed()
    }

    @Test
    fun logoutColumn_whenUiStateIsSuccess_and_propertyIsExitEnableIsTrue_showsCorrectly() {
        val pagingErrorStub = PagingErrorStub.create(isError = false)
        val exitStub = ExitStub.create(isExitEnabled = true)
        val profileStub = ProfileStub.create()
        val photoItemsStub = PhotoItemsStub.create()
        composeTestRule.setContent {
            ProfileScreen(
                uiState = ProfileContract.State.Success(
                    profileModel = profileStub,
                    photoItemModels = photoItemsStub,
                    isPagingLoadingError = pagingErrorStub,
                    isExitEnabled = exitStub
                ),
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onExitIconClick = {},
                onCanceledLogoutClick = {},
                onConfirmedLogoutClick = {}
            )
        }
        composeTestRule.onNodeWithTag(ProfileTags.LOGOUT_COLUMN).assertIsDisplayed()
    }

}