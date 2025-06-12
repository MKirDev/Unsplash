package com.mkirdev.unsplash.data.storages.database.dao.collection

import androidx.room.Dao
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.collection.PhotoFromCollectionDto
import com.mkirdev.unsplash.data.storages.database.entities.CollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity

@Dao
interface PhotoFromCollectionDao {
    @Query(
        "SELECT p.${PhotoEntity.ID}, p.${PhotoEntity.WIDTH}, "
                + "p.${PhotoEntity.HEIGHT}, p.${PhotoEntity.IMAGE_URL}, "
                + "p.${PhotoEntity.DOWNLOAD_LINK}, p.${PhotoEntity.LIKES}, "
                + "u.${UserEntity.ID}, u.${UserEntity.FULL_NAME}, u.${UserEntity.USERNAME},"
                + "u.${UserEntity.IMAGE_URL}, u.${UserEntity.BIO}, u.${UserEntity.LOCATION}, "
                + "c.${CollectionEntity.NAME}, rt.${ReactionsTypeEntity.LIKED} "
                + "FROM ${PhotoEntity.TABLE_NAME} p "
                + "JOIN ${PhotoReactionsEntity.TABLE_NAME} pr "
                + "ON pr.${PhotoReactionsEntity.PHOTO_ID} = p.${PhotoEntity.ID} "
                + "JOIN ${ReactionsTypeEntity.TABLE_NAME} rt "
                + "ON rt.${ReactionsTypeEntity.ID} = pr.${PhotoReactionsEntity.REACTIONS_ID} "
                + "JOIN ${PhotoCollectionEntity.TABLE_NAME} pc "
                + "ON pc.${PhotoCollectionEntity.PHOTO_ID} = p.${PhotoEntity.ID} "
                + "JOIN ${CollectionEntity.TABLE_NAME} c "
                + "ON c.${CollectionEntity.ID} = pc.${PhotoCollectionEntity.COLLECTION_ID} "
                + "JOIN ${UserEntity.TABLE_NAME} u "
                + "ON u.${UserEntity.ID} = p.${PhotoEntity.USER_ID} "
    )
    fun getPhotosFromCollection(): List<PhotoFromCollectionDto>
}