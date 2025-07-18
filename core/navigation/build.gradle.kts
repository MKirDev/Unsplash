import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

val APP_EXTERNAL_SCHEME: String = gradleLocalProperties(rootDir, providers).getProperty("APP_EXTERNAL_SCHEME")
val APP_INTERNAL_SCHEME: String = gradleLocalProperties(rootDir, providers).getProperty("APP_INTERNAL_SCHEME")
val HOST: String = gradleLocalProperties(rootDir, providers).getProperty("HOST")
val PATH: String = gradleLocalProperties(rootDir, providers).getProperty("PATH")

android {
    namespace = "com.mkirdev.unsplash.core.navigation"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        resValue("string","app_internal_scheme", "\"$APP_INTERNAL_SCHEME\"")
        resValue("string", "app_external_scheme", "\"$APP_EXTERNAL_SCHEME\"")
        resValue("string","host", "\"$HOST\"")
        resValue("string","path", "\"$PATH\"")
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
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
}

dependencies {

    // core
    implementation(libs.core.ktx)

    // navigation
    implementation(libs.navigation)
    implementation(libs.androidx.navigation.runtime.ktx)
}