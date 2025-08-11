package com.mkirdev.unsplash.data.storages.database.dao.liked

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.liked.PhotoLikedJoinedDto
import com.mkirdev.unsplash.data.storages.database.entities.liked.PhotoLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.PhotoReactionsLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.ReactionsLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.UserLikedEntity
import com.mkirdev.unsplash.data.storages.database.view.PhotoLikedJoinedView

@Dao
interface PhotoLikedJoinedDao {
//    @Query(
//        "SELECT p.${PhotoLikedEntity.ID}, p.${PhotoLikedEntity.PHOTO_ID}, p.${PhotoLikedEntity.WIDTH}, "
//                + "p.${PhotoLikedEntity.HEIGHT}, p.${PhotoLikedEntity.IMAGE_URL}, "
//                + "p.${PhotoLikedEntity.DOWNLOAD_LINK}, p.${PhotoLikedEntity.HTML_LINK}, "
//                + "p.${PhotoLikedEntity.LIKES}, rt.${ReactionsLikedEntity.LIKED}, "
//                + "u.${UserLikedEntity.USER_ID}, u.${UserLikedEntity.FULL_NAME}, "
//                + "u.${UserLikedEntity.USERNAME}, u.${UserLikedEntity.IMAGE_URL}, "
//                + "u.${UserLikedEntity.BIO}, u.${UserLikedEntity.LOCATION} "
//                + "FROM ${PhotoLikedEntity.TABLE_NAME} p "
//                + "JOIN ${PhotoReactionsLikedEntity.TABLE_NAME} pr "
//                + "ON pr.${PhotoReactionsLikedEntity.PHOTO_ID} = p.${PhotoLikedEntity.PHOTO_ID} "
//                + "JOIN ${ReactionsLikedEntity.TABLE_NAME} rt "
//                + "ON rt.${ReactionsLikedEntity.PHOTO_ID} = pr.${PhotoReactionsLikedEntity.REACTIONS_ID} "
//                + "JOIN ${UserLikedEntity.TABLE_NAME} u "
//                + "ON u.${UserLikedEntity.USER_ID} = p.${PhotoLikedEntity.USER_ID} "
//                + "WHERE rt.${ReactionsLikedEntity.LIKED} == 1 ORDER BY p.${PhotoLikedEntity.ID} ASC"
//    )
//    fun getLikedJoinedPhotos(): PagingSource<Int, PhotoLikedJoinedDto>

    @Query("SELECT * FROM ${PhotoLikedJoinedView.VIEW_NAME} ORDER BY ${PhotoLikedJoinedView.ID} ASC")
    fun getLikedJoinedPhotos(): PagingSource<Int, PhotoLikedJoinedView>
}