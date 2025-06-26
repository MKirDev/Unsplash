package com.mkirdev.unsplash.profile.utils.stubs

import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.models.UserModel
import com.mkirdev.unsplash.profile.models.ProfileModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileStub {
    companion object {
        fun create() = ProfileModel(
            id = "IOsig125",
            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            username = "spensersembrat",
            fullName = "Spenser Sembrat",
            bio = "Preview bio",
            location = "Preview location",
            totalLikes = "1000",
            downloads = "1000",
            email = "preview@example.com"
        )
    }
}

class PhotoItemsStub {
    companion object {
        fun create() = MutableStateFlow(
            PagingData.from(
                persistentListOf(
                    PhotoItemModel(
                        id = "IOsig125yhdf",
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
                        likes = "3.4k",
                        isLiked = false
                    ), PhotoItemModel(
                        id = "IOsig126yhdf",
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
                        likes = "3.4k",
                        isLiked = false
                    ),
                    PhotoItemModel(
                        id = "IOsig127yhdf",
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
                        likes = "3.4k",
                        isLiked = false
                    ), PhotoItemModel(
                        id = "IOsig128yhdf",
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
                        likes = "3.4k",
                        isLiked = false
                    ), PhotoItemModel(
                        id = "IOsig129yhdf",
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
                        likes = "3.4k",
                        isLiked = false
                    )
                )
            )
        )
    }
}

class ErrorStub {
    companion object {
        fun create() = "Test error"
    }
}

class PagingErrorStub {
    companion object {
        fun create(isError: Boolean) = isError
    }
}

class ExitStub {
    companion object {
        fun create(isExitEnabled: Boolean) = isExitEnabled
    }
}

class UpdatedCountStub {

    companion object {
        fun create() = 0
    }

}