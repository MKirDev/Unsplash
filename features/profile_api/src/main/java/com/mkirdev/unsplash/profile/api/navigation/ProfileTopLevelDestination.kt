package com.mkirdev.unsplash.profile.api.navigation

import com.mkirdev.unsplash.core.navigation.TopLevelDestination
import com.mkirdev.unsplash.core.ui.R

object ProfileTopLevelDestination : TopLevelDestination {
    override val route: String = "profile"
    override val iconId: Int = R.drawable.ic_baseline_person_outline_24
    override val titleId: Int = R.string.profile
}