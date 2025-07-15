package com.mkirdev.unsplash.data.storages.database.dao.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedDto
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity

@Dao
interface PhotoSearchDao {
    @Query(
        "SELECT p.${PhotoEntity.ID}, p.${PhotoEntity.WIDTH}, "
                + "p.${PhotoEntity.HEIGHT}, p.${PhotoEntity.IMAGE_URL}, "
                + "p.${PhotoEntity.DOWNLOAD_LINK}, p.${PhotoEntity.HTML_LINK}, "
                + "p.${PhotoEntity.LIKES}, p.${PhotoEntity.SEARCH_TYPE}, "
                + "p.${PhotoEntity.POSITION}, rt.${ReactionsTypeEntity.LIKED}, "
                + "u.${UserEntity.ID}, u.${UserEntity.FULL_NAME}, "
                + "u.${UserEntity.USERNAME}, u.${UserEntity.IMAGE_URL}, "
                + "u.${UserEntity.BIO}, u.${UserEntity.LOCATION} "
                + "FROM ${PhotoEntity.TABLE_NAME} p "
                + "JOIN ${PhotoReactionsEntity.TABLE_NAME} pr "
                + "ON pr.${PhotoReactionsEntity.PHOTO_ID} = p.${PhotoEntity.ID} "
                + "JOIN ${ReactionsTypeEntity.TABLE_NAME} rt "
                + "ON rt.${ReactionsTypeEntity.ID} = pr.${PhotoReactionsEntity.REACTIONS_ID} "
                + "JOIN ${UserEntity.TABLE_NAME} u "
                + "ON u.${UserEntity.ID} = p.${PhotoEntity.USER_ID} WHERE p.${PhotoEntity.SEARCH_TYPE} = 1"
    )
    fun getSearchPhotos(): PagingSource<Int, PhotoFeedDto>
}