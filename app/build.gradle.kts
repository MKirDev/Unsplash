import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.compose.compiler)
}

val REDIRECT_SCHEME: String = gradleLocalProperties(rootDir, providers).getProperty("REDIRECT_SCHEME")
val UNSPLASH_BASE_URL: String = gradleLocalProperties(rootDir, providers).getProperty("UNSPLASH_BASE_URL")

val HOST: String = gradleLocalProperties(rootDir, providers).getProperty("HOST")
val PATH: String = gradleLocalProperties(rootDir, providers).getProperty("PATH")
val APP_EXTERNAL_SCHEME = gradleLocalProperties(rootDir, providers).getProperty("APP_EXTERNAL_SCHEME")

android {
    namespace = "com.mkirdev.unsplash"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.mkirdev.unsplash"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["appAuthRedirectScheme"] = "\"$REDIRECT_SCHEME\""
        android.buildFeatures.buildConfig = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resValue("string","host","\"${HOST}\"")
        resValue("string","path","\"${PATH}\"")
        resValue("string","app_external_scheme","\"${APP_EXTERNAL_SCHEME}\"")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String","UNSPLASH_BASE_URL", "\"${UNSPLASH_BASE_URL}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(JavaVersion.VERSION_17.toString()))
        }
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:contract:usecase"))

    implementation(project(":data"))

    implementation(project(":domain"))

    implementation(project(":features:auth_api"))
    implementation(project(":features:auth_impl"))

    implementation(project(":features:bottom_menu_api"))
    implementation(project(":features:bottom_menu_impl"))

    implementation(project(":features:collection_details_api"))
    implementation(project(":features:collection_details_impl"))

    implementation(project(":features:collections_api"))
    implementation(project(":features:collections_impl"))

    implementation(project(":features:content_creation_api"))
    implementation(project(":features:content_creation_impl"))

    implementation(project(":features:notification_api"))
    implementation(project(":features:notification_impl"))

    implementation(project(":features:details_api"))
    implementation(project(":features:details_impl"))

    implementation(project(":features:onboarding_api"))
    implementation(project(":features:onboarding_impl"))

    implementation(project(":features:photo_explore_api"))
    implementation(project(":features:photo_explore_impl"))

    implementation(project(":features:photo_feed_api"))
    implementation(project(":features:photo_feed_impl"))

    implementation(project(":features:photo_search_api"))
    implementation(project(":features:photo_search_impl"))

    implementation(project(":features:profile_api"))
    implementation(project(":features:profile_impl"))

    implementation(project(":features:social_collections_api"))
    implementation(project(":features:social_collections_impl"))

    implementation(project(":features:upload_and_track_api"))
    implementation(project(":features:upload_and_track_impl"))

    // di
    implementation(libs.dagger)
    implementation(libs.androidx.lifecycle.service)
    ksp(libs.dagger.compiler)

    // core
    implementation(libs.core.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.kotlinx.coroutines.rx3)
    implementation(libs.reactivex.rxjava)

    // data
    implementation(libs.datastore)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.rxjava)
    implementation(libs.okhttp3.interceptor)
    implementation(libs.gson)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    // app
    implementation(libs.appauth)

    // navigation
    implementation(libs.navigation)
    implementation(libs.androidx.navigation.runtime.ktx)

    // presentation
    implementation(libs.lifecycle.runtime.ktx)

    // presentation compose
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
}