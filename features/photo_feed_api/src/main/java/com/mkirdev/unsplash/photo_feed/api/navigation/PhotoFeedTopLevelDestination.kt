package com.mkirdev.unsplash.photo_feed.api.navigation

import com.mkirdev.unsplash.core.navigation.TopLevelDestination
import com.mkirdev.unsplash.core.ui.R


object PhotoFeedTopLevelDestination : TopLevelDestination {
    override val route: String = "photo_feed"
    override val iconId: Int = R.drawable.ic_outline_home_24
    override val titleId: Int = R.string.photo_feed
}