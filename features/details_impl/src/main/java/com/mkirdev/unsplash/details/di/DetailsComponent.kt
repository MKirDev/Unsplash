package com.mkirdev.unsplash.details.di

import com.mkirdev.unsplash.details.impl.DetailsViewModelFactoryAssisted
import dagger.Component

@Component(dependencies = [DetailsDependencies::class])
@DetailsScope
internal interface DetailsComponent : DetailsDependencies {

    val factoryAssisted: DetailsViewModelFactoryAssisted

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: DetailsDependencies): Builder
        fun build() : DetailsComponent
    }
}