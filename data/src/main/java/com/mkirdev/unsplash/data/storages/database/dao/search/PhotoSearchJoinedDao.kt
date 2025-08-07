package com.mkirdev.unsplash.data.storages.database.dao.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.search.PhotoSearchJoinedDto
import com.mkirdev.unsplash.data.storages.database.entities.search.PhotoReactionsSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.PhotoSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.ReactionsSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.UserSearchEntity

@Dao
interface PhotoSearchJoinedDao {
    @Query(
        "SELECT p.${PhotoSearchEntity.ID}, p.${PhotoSearchEntity.PHOTO_ID}, p.${PhotoSearchEntity.WIDTH}, "
                + "p.${PhotoSearchEntity.HEIGHT}, p.${PhotoSearchEntity.IMAGE_URL}, "
                + "p.${PhotoSearchEntity.DOWNLOAD_LINK}, p.${PhotoSearchEntity.HTML_LINK}, "
                + "p.${PhotoSearchEntity.LIKES}, rt.${ReactionsSearchEntity.LIKED}, "
                + "u.${UserSearchEntity.USER_ID}, u.${UserSearchEntity.FULL_NAME}, "
                + "u.${UserSearchEntity.USERNAME}, u.${UserSearchEntity.IMAGE_URL}, "
                + "u.${UserSearchEntity.BIO}, u.${UserSearchEntity.LOCATION} "
                + "FROM ${PhotoSearchEntity.TABLE_NAME} p "
                + "JOIN ${PhotoReactionsSearchEntity.TABLE_NAME} pr "
                + "ON pr.${PhotoReactionsSearchEntity.PHOTO_ID} = p.${PhotoSearchEntity.PHOTO_ID} "
                + "JOIN ${ReactionsSearchEntity.TABLE_NAME} rt "
                + "ON rt.${ReactionsSearchEntity.PHOTO_ID} = pr.${PhotoReactionsSearchEntity.REACTIONS_ID} "
                + "JOIN ${UserSearchEntity.TABLE_NAME} u "
                + "ON u.${UserSearchEntity.USER_ID} = p.${PhotoSearchEntity.USER_ID}"
    )
    fun getSearchJoinedPhotos(): PagingSource<Int, PhotoSearchJoinedDto>
}