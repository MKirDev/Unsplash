package com.mkirdev.unsplash.collections

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.collections.impl.CollectionsContract
import com.mkirdev.unsplash.collections.impl.CollectionsScreen
import com.mkirdev.unsplash.collections.impl.CollectionsTags
import com.mkirdev.unsplash.collections.utils.stubs.CollectionItemsStub
import com.mkirdev.unsplash.collections.utils.stubs.CollectionsErrorStub
import com.mkirdev.unsplash.collections.utils.stubs.PagingErrorStub
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionsScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingIndicator_whenUiStateIsLoading_isDisplayed() {
        composeTestRule.setContent {
            CollectionsScreen(
                uiState = CollectionsContract.State.Loading,
                onCollectionClick = {},
                onLoadError = { },
                onCloseFieldClick = {})
        }

        composeTestRule.onNodeWithTag(CollectionsTags.LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun field_whenUiStateIsFailure_showsError() {
        val errorStub = CollectionsErrorStub.create()
        composeTestRule.setContent {
            CollectionsScreen(
                uiState = CollectionsContract.State.Failure(
                    error = errorStub
                ),
                onCollectionClick = {},
                onLoadError = { },
                onCloseFieldClick = {})
        }

        composeTestRule.onNodeWithTag(CollectionsTags.ERROR_FIELD).assertIsDisplayed()
    }

    @Test
    fun paging_whenLoadStateIsAppendAndHasError_showsError() {
        val collectionItemsStub = CollectionItemsStub.create()
        val pagingErrorStub = PagingErrorStub.create()
        composeTestRule.setContent {
            CollectionsScreen(
                uiState = CollectionsContract.State.Success(
                    collectionItemsModel = collectionItemsStub,
                    isPagingLoadingError = pagingErrorStub
                ),
                onCollectionClick = {},
                onLoadError = { },
                onCloseFieldClick = {})
        }
        composeTestRule.onNodeWithTag(CollectionsTags.LAZY_COLUMN)
            .performScrollToNode(hasTestTag(CollectionsTags.PAGING_ERROR_FIELD)).assertIsDisplayed()
    }
}