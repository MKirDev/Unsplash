package com.mkirdev.unsplash.photo_search.di

import com.mkirdev.unsplash.photo_search.impl.PhotoSearchViewModelFactory
import dagger.Component

@Component(modules = [PhotoSearchModule::class], dependencies = [PhotoSearchDependencies::class])
@PhotoSearchScope
internal interface PhotoSearchComponent {

    val photoSearchViewModelFactory: PhotoSearchViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: PhotoSearchDependencies): Builder
        fun build(): PhotoSearchComponent
    }
}