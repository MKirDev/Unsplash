package com.mkirdev.unsplash.data.storages.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE VIEW `liked_photos_view` AS SELECT " +
                    "p.id AS id, p.photo_id AS photo_id, p.width AS width, p.height AS height, " +
                    "p.image_url AS image_url, p.download_link AS download_link, p.html_link AS html_link, " +
                    "p.likes AS likes, rt.liked AS liked, " +
                    "u.user_id AS user_id, u.user_full_name AS user_full_name, u.user_username AS user_username, " +
                    "u.user_image_url AS user_image_url, u.user_bio AS user_bio, u.user_location AS user_location " +
                    "FROM photo_liked p " +
                    "JOIN photo_reactions_liked pr ON pr.photo_id = p.photo_id " +
                    "JOIN reactions_liked rt ON rt.photo_id = pr.reactions_id " +
                    "JOIN user_liked u ON u.user_id = p.user_id " +
                    "WHERE rt.liked = 1"
        )
    }
}