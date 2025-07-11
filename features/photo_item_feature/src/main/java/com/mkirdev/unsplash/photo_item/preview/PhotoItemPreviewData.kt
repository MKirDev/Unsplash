package com.mkirdev.unsplash.photo_item.preview

import androidx.compose.ui.unit.dp
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.models.UserModel

internal fun createPhotoItemPreviewData() = PhotoItemModel(
    id = "IOsig125yhdf",
    imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    width = 100.dp,
    height = 250.dp,
    aspectRatioImage = 1f,
    user = UserModel(
        name = "Spenser Sembrat",
        username = "spensersembrat",
        userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    ),
    downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    downloads = "100",
    likes = "3.4k",
    isLiked = false
)