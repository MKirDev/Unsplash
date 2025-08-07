package com.mkirdev.unsplash.profile.preview

import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.models.UserModel
import com.mkirdev.unsplash.profile.models.ProfileModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

fun createProfileModelPreviewData() = ProfileModel(
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

fun createPhotoItemModelsPreviewData(): Flow<PagingData<PhotoItemModel>> = MutableStateFlow(
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
                likes = "3.4k",
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
                likes = "3.4k",
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
                likes = "3.4k",
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
                likes = "3.4k",
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
                likes = "3.4k",
                isLiked = false
            )
        )
    )
)

