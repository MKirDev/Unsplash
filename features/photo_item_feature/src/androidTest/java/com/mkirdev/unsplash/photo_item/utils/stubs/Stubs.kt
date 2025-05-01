package com.mkirdev.unsplash.photo_item.utils.stubs

import com.mkirdev.unsplash.photo_item.api.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.api.models.UserModel

class PhotoItemStub {
    companion object {
        fun create(isLiked: Boolean) = PhotoItemModel(
            id = "test_id",
            imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            aspectRatioImage = 2.5f,
            user = UserStub.create(),
            likes = "1000",
            isLiked = isLiked,
        )
    }
}

class UserStub {
    companion object {
        fun create() = UserModel(
            name = "Spenser Sembrat",
            userName = "@spensersembrat",
            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )
    }
}