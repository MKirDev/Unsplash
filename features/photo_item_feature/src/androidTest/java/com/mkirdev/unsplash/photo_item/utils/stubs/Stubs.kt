package com.mkirdev.unsplash.photo_item.utils.stubs

import androidx.compose.ui.unit.dp
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.models.UserModel

class PhotoItemStub {
    companion object {
        fun create(isLiked: Boolean) = PhotoItemModel(
            position = 0,
            photoId = "test_id",
            imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            width = 150.dp,
            height = 250.dp,
            aspectRatioImage = 2.5f,
            user = UserStub.create(),
            downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            downloads = "100",
            likes = "1000",
            isLiked = isLiked,
        )
    }
}

class UserStub {
    companion object {
        fun create() = UserModel(
            name = "Spenser Sembrat",
            username = "spensersembrat",
            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )
    }
}