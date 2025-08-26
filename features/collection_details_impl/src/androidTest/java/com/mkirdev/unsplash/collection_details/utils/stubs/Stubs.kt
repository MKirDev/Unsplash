package com.mkirdev.unsplash.collection_details.utils.stubs

import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.mkirdev.unsplash.collection_details.models.CollectionDetailsModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.models.UserModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow

class CollectionDetailsStub {
    companion object {
        fun create() = CollectionDetailsModel(
            title = "New collection",
            description = "Showcasing blooming flowers, fresh greenery, and the vibrant colors of spring.",
            totalPhotos = "12",
            previewPhotoUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            username = "spensersembrat"
        )
    }
}
class PhotoItemsStub {
    companion object {
        fun create() =  MutableStateFlow(
            PagingData.from(
                persistentListOf(
                    PhotoItemModel(
                        position = 0,
                        photoId = "IOsig125yhdf",
                        imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        width = 100.dp,
                        height = 250.dp,
                        aspectRatioImage = 0.5f,
                        user = UserModel(
                            name = "Spenser Sembrat",
                            username = "spensersembrat",
                            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        ),
                        downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        downloads = "100",
                        likes = "3400",
                        isLiked = false
                    ), PhotoItemModel(
                        position = 1,
                        photoId = "IOsig126yhdf",
                        imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        width = 100.dp,
                        height = 150.dp,
                        aspectRatioImage = 1.5f,
                        user = UserModel(
                            name = "Spenser Sembrat",
                            username = "spensersembrat",
                            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        ),
                        downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        downloads = "100",
                        likes = "3400",
                        isLiked = false
                    ),
                    PhotoItemModel(
                        position = 2,
                        photoId = "IOsig127yhdf",
                        imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        width = 100.dp,
                        height = 200.dp,
                        aspectRatioImage = 0.66f,
                        user = UserModel(
                            name = "Spenser Sembrat",
                            username = "spensersembrat",
                            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        ),
                        downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        downloads = "100",
                        likes = "3400",
                        isLiked = false
                    ), PhotoItemModel(
                        position = 3,
                        photoId = "IOsig128yhdf",
                        imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        width = 100.dp,
                        height = 270.dp,
                        aspectRatioImage = 0.66f,
                        user = UserModel(
                            name = "Spenser Sembrat",
                            username = "spensersembrat",
                            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        ),
                        downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        downloads = "100",
                        likes = "3400",
                        isLiked = false
                    ), PhotoItemModel(
                        position = 4,
                        photoId = "IOsig129yhdf",
                        imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        width = 100.dp,
                        height = 250.dp,
                        aspectRatioImage = 0.66f,
                        user = UserModel(
                            name = "Spenser Sembrat",
                            username = "spensersembrat",
                            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        ),
                        downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        downloads = "100",
                        likes = "3400",
                        isLiked = false
                    )
                )
            )
        )
    }
}

class ErrorStub {
    companion object {
        fun create() = "Test Error"
    }
}

class PagingErrorStub {
    companion object {
        fun create(isError: Boolean) = isError
    }
}

class UpdatedCountStub {

    companion object {
        fun create() = 0
    }

}