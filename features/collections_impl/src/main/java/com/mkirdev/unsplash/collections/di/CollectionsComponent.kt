package com.mkirdev.unsplash.collections.di

import com.mkirdev.unsplash.collections.impl.CollectionsViewModelFactory
import dagger.Component

@Component(
    modules = [CollectionsModule::class],
    dependencies = [CollectionsDependencies::class]
)
@CollectionsScope
internal interface CollectionsComponent : CollectionsDependencies {
    val collectionsViewModelFactory: CollectionsViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: CollectionsDependencies): Builder
        fun build(): CollectionsComponent
    }
}