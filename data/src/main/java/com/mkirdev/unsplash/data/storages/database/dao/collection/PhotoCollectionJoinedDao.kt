package com.mkirdev.unsplash.data.storages.database.dao.collection

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.collection.PhotoCollectionJoinedDto
import com.mkirdev.unsplash.data.storages.database.entities.collection.PhotoCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.PhotoReactionsCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.ReactionsCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.UserCollectionEntity


@Dao
interface PhotoCollectionJoinedDao {
    @Query(
        "SELECT p.${PhotoCollectionEntity.ID}, p.${PhotoCollectionEntity.PHOTO_ID}, p.${PhotoCollectionEntity.WIDTH}, "
                + "p.${PhotoCollectionEntity.HEIGHT}, p.${PhotoCollectionEntity.IMAGE_URL}, "
                + "p.${PhotoCollectionEntity.DOWNLOAD_LINK}, p.${PhotoCollectionEntity.HTML_LINK}, "
                + "p.${PhotoCollectionEntity.LIKES}, p.${PhotoCollectionEntity.COLLECTION_ID}, rt.${ReactionsCollectionEntity.LIKED}, "
                + "u.${UserCollectionEntity.USER_ID}, u.${UserCollectionEntity.FULL_NAME}, "
                + "u.${UserCollectionEntity.USERNAME}, u.${UserCollectionEntity.IMAGE_URL}, "
                + "u.${UserCollectionEntity.BIO}, u.${UserCollectionEntity.LOCATION} "
                + "FROM ${PhotoCollectionEntity.TABLE_NAME} p "
                + "JOIN ${PhotoReactionsCollectionEntity.TABLE_NAME} pr "
                + "ON pr.${PhotoReactionsCollectionEntity.PHOTO_ID} = p.${PhotoCollectionEntity.PHOTO_ID} "
                + "JOIN ${ReactionsCollectionEntity.TABLE_NAME} rt "
                + "ON rt.${ReactionsCollectionEntity.PHOTO_ID} = pr.${PhotoReactionsCollectionEntity.REACTIONS_ID} "
                + "JOIN ${UserCollectionEntity.TABLE_NAME} u "
                + "ON u.${UserCollectionEntity.USER_ID} = p.${PhotoCollectionEntity.USER_ID}"
    )
    fun getCollectionJoinedPhotos(): PagingSource<Int, PhotoCollectionJoinedDto>
}