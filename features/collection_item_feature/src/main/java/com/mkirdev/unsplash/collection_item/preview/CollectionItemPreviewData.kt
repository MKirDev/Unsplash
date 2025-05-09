package com.mkirdev.unsplash.collection_item.preview

import com.mkirdev.unsplash.collection_item.models.CollectionItemModel
import com.mkirdev.unsplash.collection_item.models.CollectionUserModel

internal fun createCollectionItemPreviewData() = CollectionItemModel(
    id = "IOsig125yhdf",
    title = "New Collections",
    totalPhotos = "100",
    user = CollectionUserModel(
        name = "Spenser Sembrat",
        userName = "spensersembrat",
        userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    ),
    coverPhotoUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
)