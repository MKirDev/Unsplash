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
include(":core:contract:usecase")
include(":data")
include(":domain")
include(":features:photo_item_feature")
include(":features:auth_api")
include(":features:auth_impl")
include(":features:onboarding_api")
include(":features:onboarding_impl")
include(":features:bottom_menu_api")
include(":features:bottom_menu_impl")
include(":features:photo_feed_api")
include(":features:photo_feed_impl")
include(":features:details_api")
include(":features:details_impl")
include(":features:collections_api")
include(":features:collections_impl")
include(":features:profile_api")
include(":features:profile_impl")
include(":features:content_creation_api")
include(":features:content_creation_impl")
include(":features:social_collections_api")
include(":features:social_collections_impl")
include(":features:upload_and_track_api")
include(":features:upload_and_track_impl")
include(":features:notification_api")
include(":features:notification_impl")
include(":features:collection_item_feature")
include(":features:collection_details_api")
include(":features:collection_details_impl")
