package com.mkirdev.unsplash.collections.impl

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.mkirdev.unsplash.core.ui.theme.item_height_348
import com.mkirdev.unsplash.core.ui.theme.item_width_64
import com.mkirdev.unsplash.core.ui.theme.padding_78
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.core.ui.widgets.StaticEmptyField
import com.mkirdev.unsplash.core.ui.widgets.TitleField
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall

@Composable
internal fun CollectionsScreen(
    uiState: CollectionsContract.State,
    onCollectionClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit
) {

    val configuration = LocalConfiguration.current

    val contentScale = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        ContentScale.FillWidth
    } else {
        ContentScale.FillBounds
    }

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

            val pagedItems: LazyPagingItems<CollectionItemModel> =
                uiState.collectionItemsModel.collectAsLazyPagingItems()

            LaunchedEffect(key1 = pagedItems.loadState, block = {
                snapshotFlow { pagedItems.loadState.append }.collect { loadState ->
                    if (loadState is LoadState.Error) onLoadError()
                }
            })

            Column {
                TitleField(
                    titleText = stringResource(id = R.string.collections_title),
                    modifier = Modifier.fillMaxWidth()
                )

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
                                    .height(item_height_348)
                                    .clickable {
                                        onCollectionClick(item.id)
                                    },
                                contentScale = contentScale,
                                photoItemModel = it,
                                userImage = {
                                    UserImageSmall(imageUrl = it.user.userImage)
                                },
                                userInfo = {
                                    UserInfoSmall(
                                        name = it.user.name,
                                        userName = it.user.username
                                    )
                                }
                            )
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

                    item {
                        StaticEmptyField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = padding_78)
                        )
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

internal object CollectionsTags {
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