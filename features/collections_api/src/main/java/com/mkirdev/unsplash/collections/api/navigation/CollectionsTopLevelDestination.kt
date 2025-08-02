package com.mkirdev.unsplash.collections.api.navigation

import com.mkirdev.unsplash.core.navigation.TopLevelDestination
import com.mkirdev.unsplash.core.ui.R

object CollectionsTopLevelDestination : TopLevelDestination {
    override val route: String = "collections"
    override val iconId: Int = R.drawable.ic_outline_star
    override val titleId: Int = R.string.collections
}