package com.mkirdev.unsplash.data.storages.database.dao.feed

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedJoinedDto
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.ReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.UserFeedEntity

@Dao
interface PhotoFeedJoinedDao {
    @Query(
        "SELECT p.${PhotoFeedEntity.ID}, p.${PhotoFeedEntity.PHOTO_ID}, p.${PhotoFeedEntity.WIDTH}, "
                + "p.${PhotoFeedEntity.HEIGHT}, p.${PhotoFeedEntity.IMAGE_URL}, "
                + "p.${PhotoFeedEntity.DOWNLOAD_LINK}, p.${PhotoFeedEntity.HTML_LINK}, "
                + "p.${PhotoFeedEntity.LIKES}, rt.${ReactionsFeedEntity.LIKED}, "
                + "u.${UserFeedEntity.USER_ID}, u.${UserFeedEntity.FULL_NAME}, "
                + "u.${UserFeedEntity.USERNAME}, u.${UserFeedEntity.IMAGE_URL}, "
                + "u.${UserFeedEntity.BIO}, u.${UserFeedEntity.LOCATION} "
                + "FROM ${PhotoFeedEntity.TABLE_NAME} p "
                + "JOIN ${PhotoReactionsFeedEntity.TABLE_NAME} pr "
                + "ON pr.${PhotoReactionsFeedEntity.PHOTO_ID} = p.${PhotoFeedEntity.PHOTO_ID} "
                + "JOIN ${ReactionsFeedEntity.TABLE_NAME} rt "
                + "ON rt.${ReactionsFeedEntity.PHOTO_ID} = pr.${PhotoReactionsFeedEntity.REACTIONS_ID} "
                + "JOIN ${UserFeedEntity.TABLE_NAME} u "
                + "ON u.${UserFeedEntity.USER_ID} = p.${PhotoFeedEntity.USER_ID}"
    )
    fun getFeedJoinedPhotos(): PagingSource<Int, PhotoFeedJoinedDto>
}
