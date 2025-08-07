package com.mkirdev.unsplash.profile.di

import com.mkirdev.unsplash.profile.impl.ProfileViewModelFactory
import dagger.Component

@Component(dependencies = [ProfileDependencies::class])
@ProfileScope
internal interface ProfileComponent : ProfileDependencies {

    val profileViewModelFactory: ProfileViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: ProfileDependencies): Builder
        fun build(): ProfileComponent
    }

}