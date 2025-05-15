package com.mkirdev.unsplash.collections.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mkirdev.unsplash.collection_item.feature.CollectionItem
import com.mkirdev.unsplash.collection_item.models.CollectionItemModel
import com.mkirdev.unsplash.collections.preview.createCollectionsPreviewData
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.item_width_64
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.core.ui.widgets.TitleField
import com.mkirdev.unsplash.core.ui.widgets.UserImageMedium
import com.mkirdev.unsplash.core.ui.widgets.UserInfoMedium

@Composable
fun CollectionsScreen(
    uiState: CollectionsContract.State,
    onCollectionClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit
) {
    when (uiState) {
        CollectionsContract.State.Idle -> {}
        CollectionsContract.State.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingIndicator(
                    modifier = Modifier
                        .width(item_width_64)
                        .testTag(CollectionsTags.LOADING_INDICATOR)
                )
            }
        }

        is CollectionsContract.State.Success -> {
            Column {
                TitleField(
                    titleText = stringResource(id = R.string.collections_title),
                    modifier = Modifier.fillMaxWidth()
                )
                val pagedItems: LazyPagingItems<CollectionItemModel> =
                    uiState.collectionItemsModel.collectAsLazyPagingItems()
                LazyColumn(modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .testTag(CollectionsTags.LAZY_COLUMN)
                ) {
                    items(
                        pagedItems.itemCount,
                        key = pagedItems.itemKey { collectionItem -> collectionItem.id }) { index ->
                        val item = pagedItems[index]
                        item?.let {
                            CollectionItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onCollectionClick(item.id)
                                    },
                                photoItemModel = it,
                                userImage = {
                                    UserImageMedium(imageUrl = it.user.userImage)
                                },
                                userInfo = {
                                    UserInfoMedium(
                                        name = it.user.name,
                                        userName = it.user.userName
                                    )
                                }
                            )
                        }
                    }
                    pagedItems.apply {
                        if (loadState.append is LoadState.Error) {
                            onLoadError()
                        }
                    }
                    when (uiState.isPagingLoadingError) {
                        true -> {
                            item {
                                ClosableErrorField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag(CollectionsTags.PAGING_ERROR_FIELD),
                                    text = stringResource(id = R.string.collections_network_error),
                                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                                    onClick = {
                                        onCloseFieldClick()
                                    }
                                )
                            }
                        }
                        false -> {
                            pagedItems.retry()
                        }

                        else -> {}
                    }
                }
            }
        }

        is CollectionsContract.State.Failure -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ClosableErrorField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .testTag(CollectionsTags.ERROR_FIELD),
                    text = uiState.error,
                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                    onClick = onCloseFieldClick
                )
            }
        }
    }
}

object CollectionsTags {
    const val LAZY_COLUMN = "CollectionsTags:LAZY_COLUMN"
    const val LOADING_INDICATOR = "CollectionsTags:LOADING_INDICATOR"
    const val ERROR_FIELD = "CollectionsTags:ERROR_FIELD"
    const val PAGING_ERROR_FIELD = "CollectionsTags:PAGING_ERROR_FIELD"
}


@Preview
@Composable
private fun CollectionsPreview() {
    UnsplashTheme(dynamicColor = false) {
        val collectionItemsModel = createCollectionsPreviewData()
        CollectionsScreen(
            uiState = CollectionsContract.State.Success(
                collectionItemsModel = collectionItemsModel,
                isPagingLoadingError = true
            ),
            onCollectionClick = {},
            onLoadError = {},
            onCloseFieldClick = {}
        )
    }
}