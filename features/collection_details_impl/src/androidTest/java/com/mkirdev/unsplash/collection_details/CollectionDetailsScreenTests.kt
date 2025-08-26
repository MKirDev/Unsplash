package com.mkirdev.unsplash.collection_details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsContract
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsScreenWrapper
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsTags
import com.mkirdev.unsplash.collection_details.utils.stubs.CollectionDetailsStub
import com.mkirdev.unsplash.collection_details.utils.stubs.ErrorStub
import com.mkirdev.unsplash.collection_details.utils.stubs.PagingErrorStub
import com.mkirdev.unsplash.collection_details.utils.stubs.PhotoItemsStub
import com.mkirdev.unsplash.collection_details.utils.stubs.UpdatedCountStub
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionDetailsScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun field_whenUiStateIsFailure_showsError() {
        val errorStub = ErrorStub.create()
        val pagingErrorStub = PagingErrorStub.create(isError = false)
        val updatedCountStub = UpdatedCountStub.create()
        val collectionDetailsStub = CollectionDetailsStub.create()
        val photoItemsStub = PhotoItemsStub.create()
        composeTestRule.setContent {
            CollectionDetailsScreenWrapper(
                uiState = CollectionDetailsContract.State.Failure(
                    error = errorStub,
                    isPagingLoadingError = pagingErrorStub,
                    updatedCount = updatedCountStub,
                    collectionDetailsModel = collectionDetailsStub,
                    photoItemModels = photoItemsStub
                ),
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onPagingRetry = {},
                onNavigateUp = {},
                onNavigateBack = {}
            )
        }

        composeTestRule.onNodeWithTag(CollectionDetailsTags.ERROR_FIELD).assertIsDisplayed()
    }

    @Test
    fun paging_whenLoadStateIsAppendAndHasError_showsError() {
        val collectionDetailsStub = CollectionDetailsStub.create()
        val photoItemsStub = PhotoItemsStub.create()
        val pagingErrorStub = PagingErrorStub.create(true)
        composeTestRule.setContent {
            CollectionDetailsScreenWrapper(
                uiState = CollectionDetailsContract.State.Success(
                    collectionDetailsModel = collectionDetailsStub,
                    photoItemModels = photoItemsStub,
                    isPagingLoadingError = pagingErrorStub
                ),
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onPagingRetry = {},
                onNavigateUp = {},
                onNavigateBack = {}
            )
        }
        composeTestRule.onNodeWithTag(CollectionDetailsTags.LAZY_COLUMN)
            .performScrollToNode(hasTestTag(CollectionDetailsTags.PAGING_ERROR_FIELD)).assertIsDisplayed()
    }

    @Test
    fun fields_whenUiStateIsFailure_and_paging_whenLoadStateIsAppendAndHasError_showErrors() {
        val errorStub = ErrorStub.create()
        val pagingErrorStub = PagingErrorStub.create(isError = true)
        val updatedCountStub = UpdatedCountStub.create()
        val collectionDetailsStub = CollectionDetailsStub.create()
        val photoItemsStub = PhotoItemsStub.create()
        composeTestRule.setContent {
            CollectionDetailsScreenWrapper(
                uiState = CollectionDetailsContract.State.Failure(
                    error = errorStub,
                    isPagingLoadingError = pagingErrorStub,
                    updatedCount = updatedCountStub,
                    collectionDetailsModel = collectionDetailsStub,
                    photoItemModels = photoItemsStub
                ),
                onPhotoItemClick = {},
                onLikeClick = {},
                onRemoveLikeClick = {},
                onDownloadClick = {},
                onLoadError = {},
                onCloseFieldClick = {},
                onPagingCloseFieldClick = {},
                onPagingRetry = {},
                onNavigateUp = {},
                onNavigateBack = {}
            )
        }

        composeTestRule.onNodeWithTag(CollectionDetailsTags.ERROR_FIELD).assertIsDisplayed()
        composeTestRule.onNodeWithTag(CollectionDetailsTags.LAZY_COLUMN)
            .performScrollToNode(hasTestTag(CollectionDetailsTags.PAGING_ERROR_FIELD)).assertIsDisplayed()
    }
}