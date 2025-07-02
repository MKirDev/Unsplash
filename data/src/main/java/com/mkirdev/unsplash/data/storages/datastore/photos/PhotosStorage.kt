package com.mkirdev.unsplash.data.storages.datastore.photos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val EMPTY_STRING = ""
class PhotosStorage {

    private val likedPhoto: MutableStateFlow<String> = MutableStateFlow(EMPTY_STRING)
    private val unlikedPhoto: MutableStateFlow<String> = MutableStateFlow(EMPTY_STRING)
    private val downloadLink: MutableStateFlow<String> = MutableStateFlow(EMPTY_STRING)

    fun addLikedPhoto(id: String) {
        likedPhoto.update { id }
    }

    fun getLikedPhoto(): Flow<String> {
        return likedPhoto.asStateFlow()
    }

    fun addUnlikedPhoto(id: String) {
        unlikedPhoto.update { id }
    }

    fun getUnlikedPhoto(): Flow<String> {
        return unlikedPhoto.asStateFlow()
    }

    fun addDownloadLink(link: String) {
        downloadLink.update { link }
    }

    fun getDownloadLink(): Flow<String> {
        return downloadLink.asStateFlow()
    }

    suspend fun clear() {
        likedPhoto.update { EMPTY_STRING }
        unlikedPhoto.update { EMPTY_STRING }
        downloadLink.update { EMPTY_STRING }
    }

}