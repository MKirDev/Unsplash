package com.mkirdev.unsplash.photo_explore.api.navigation

import com.mkirdev.unsplash.core.navigation.TopLevelDestination
import com.mkirdev.unsplash.core.ui.R

object PhotoExploreTopLevelDestination : TopLevelDestination {
    override val route: String = "photo_explore"
    override val iconId: Int = R.drawable.ic_outline_home_24
    override val titleId: Int = R.string.photo_explore
}