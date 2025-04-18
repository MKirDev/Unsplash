pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Unsplash"
include(":app")
include(":core:ui")
include(":core:navigation")
include(":data")
include(":domain")
include(":features:photo_item_api")
include(":features:photo_item_impl")
include(":features:auth_api")
include(":features:auth_impl")
include(":features:onboarding_api")
include(":features:onboarding_impl")
include(":features:bottom_menu_api")
include(":features:bottom_menu_impl")
include(":features:photo_feed_api")
include(":features:photo_feed_impl")
include(":features:details_api")
