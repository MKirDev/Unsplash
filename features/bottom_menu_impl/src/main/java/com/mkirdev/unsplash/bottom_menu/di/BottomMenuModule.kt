package com.mkirdev.unsplash.bottom_menu.di

import com.mkirdev.unsplash.bottom_menu.impl.BottomMenuViewModelFactory
import com.mkirdev.unsplash.core.navigation.IconicTopDestinations
import dagger.Module
import dagger.Provides

@Module
internal class BottomMenuModule {

    @Provides
    fun provideBottomMenuViewModelFactory(
        iconicTopDestinations: IconicTopDestinations,
    ): BottomMenuViewModelFactory {
        return BottomMenuViewModelFactory(
            iconicTopDestination = iconicTopDestinations
        )
    }
}