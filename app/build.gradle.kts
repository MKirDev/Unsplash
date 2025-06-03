import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
}

val REDIRECT_SCHEME: String = gradleLocalProperties(rootDir).getProperty("REDIRECT_SCHEME")
val UNSPLASH_AUTH_BASE_URI: String = gradleLocalProperties(rootDir).getProperty("UNSPLASH_AUTH_BASE_URI")

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
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String","UNSPLASH_AUTH_BASE_URI", "\"${UNSPLASH_AUTH_BASE_URI}\"")
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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
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

    implementation(project(":features:details_api"))
    implementation(project(":features:details_impl"))

    implementation(project(":features:onboarding_api"))
    implementation(project(":features:onboarding_impl"))

    implementation(project(":features:photo_feed_api"))
    implementation(project(":features:photo_feed_impl"))

    implementation(project(":features:profile_api"))
    implementation(project(":features:profile_impl"))

    implementation(project(":features:social_collections_api"))
    implementation(project(":features:social_collections_impl"))

    implementation(project(":features:upload_and_track_api"))
    implementation(project(":features:upload_and_track_impl"))

    // di
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // core
    implementation(libs.core.ktx)

    // data
    implementation(libs.datastore)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.interceptor)
    implementation(libs.gson)

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