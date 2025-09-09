package com.mkirdev.unsplash.bottom_menu.di

import com.mkirdev.unsplash.bottom_menu.impl.BottomMenuViewModelFactory
import com.mkirdev.unsplash.core.navigation.IconicTopDestinations
import dagger.Component

@Component(modules = [BottomMenuModule::class], dependencies = [BottomMenuDependencies::class])
@BottomMenuScope
internal interface BottomMenuComponent : BottomMenuDependencies {

    override val iconicTopDestination: IconicTopDestinations
    val bottomMenuViewModelFactory: BottomMenuViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: BottomMenuDependencies): Builder
        fun build() : BottomMenuComponent
    }

}