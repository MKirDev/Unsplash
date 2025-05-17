package com.mkirdev.unsplash.photo_feed.utils.stubs

import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.models.UserModel
import kotlinx.collections.immutable.persistentListOf
import kotlin.random.Random

private const val MIN_VALUE = 0
private const val MAX_VALUE = 10000

class PhotoFeedStub {
    companion object {
        fun create() = persistentListOf(
            createPhotoItemModel(Random.nextInt(MIN_VALUE, MAX_VALUE)),
            createPhotoItemModel(Random.nextInt(MIN_VALUE, MAX_VALUE)),
            createPhotoItemModel(Random.nextInt(MIN_VALUE, MAX_VALUE)),
            createPhotoItemModel(Random.nextInt(MIN_VALUE, MAX_VALUE)),
            createPhotoItemModel(Random.nextInt(MIN_VALUE, MAX_VALUE)),
        )

        private fun createPhotoItemModel(number: Int) = PhotoItemModel(
            id = "IOsig${number}yhdf",
            imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            aspectRatioImage = 1f,
            user = createUserModel(),
            downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            downloads = "100",
            likes = "3.4k",
            isLiked = false
        )

        private fun createUserModel() = UserModel(
            name = "Spenser Sembrat",
            username = "@spensersembrat",
            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        )
    }
}


class PhotoFeedSearchStub {
    companion object {
        fun create() = ""
    }
}

class PhotoFeedErrorStub {
    companion object {
        fun create() = "Test Error"
    }
}

class UpdatedCountStub {
    companion object {
        fun create() = 0
    }
}