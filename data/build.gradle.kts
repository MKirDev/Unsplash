import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
}

val UNSPLASH_AUTH_BASE_URI: String = gradleLocalProperties(rootDir, providers).getProperty("UNSPLASH_AUTH_BASE_URI")
val TOKEN_URI: String = gradleLocalProperties(rootDir, providers).getProperty("TOKEN_URI")
val CLIENT_ID: String = gradleLocalProperties(rootDir, providers).getProperty("CLIENT_ID")
val CLIENT_SECRET: String = gradleLocalProperties(rootDir, providers).getProperty("CLIENT_SECRET")
val REDIRECT_URI: String = gradleLocalProperties(rootDir, providers).getProperty("REDIRECT_URI")
val GRANT_TYPE: String = gradleLocalProperties(rootDir, providers).getProperty("GRANT_TYPE")
val SCOPE: String = gradleLocalProperties(rootDir, providers).getProperty("SCOPE")

val PATH_LIST_PHOTOS: String = gradleLocalProperties(rootDir, providers).getProperty("PATH_LIST_PHOTOS")
val FIRST_PATH_LIKE_PHOTO: String = gradleLocalProperties(rootDir, providers).getProperty("FIRST_PATH_LIKE_PHOTO")
val SECOND_PATH_LIKE_PHOTO: String = gradleLocalProperties(rootDir, providers).getProperty("SECOND_PATH_LIKE_PHOTO")
val FIRST_PATH_UNLIKE_PHOTO: String = gradleLocalProperties(rootDir, providers).getProperty("FIRST_PATH_UNLIKE_PHOTO")
val SECOND_PATH_UNLIKE_PHOTO: String = gradleLocalProperties(rootDir, providers).getProperty("SECOND_PATH_UNLIKE_PHOTO")
val PATH_PHOTO: String = gradleLocalProperties(rootDir, providers).getProperty("PATH_PHOTO")

val PATH_SEARCH_PHOTOS: String = gradleLocalProperties(rootDir, providers).getProperty("PATH_SEARCH_PHOTOS")

android {
    namespace = "com.mkirdev.unsplash.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        android.buildFeatures.buildConfig = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String","UNSPLASH_AUTH_BASE_URI", "\"${UNSPLASH_AUTH_BASE_URI}\"")
            buildConfigField("String","TOKEN_URI", "\"${TOKEN_URI}\"")
            buildConfigField("String","CLIENT_ID","\"$CLIENT_ID\"")
            buildConfigField("String","CLIENT_SECRET","\"$CLIENT_SECRET\"")
            buildConfigField("String","REDIRECT_URI","\"$REDIRECT_URI\"")
            buildConfigField("String","GRANT_TYPE","\"$GRANT_TYPE\"")
            buildConfigField("String","SCOPE","\"$SCOPE\"")

            buildConfigField("String","PATH_LIST_PHOTOS", "\"${PATH_LIST_PHOTOS}\"")
            buildConfigField("String","FIRST_PATH_LIKE_PHOTO", "\"${FIRST_PATH_LIKE_PHOTO}\"")
            buildConfigField("String","SECOND_PATH_LIKE_PHOTO", "\"${SECOND_PATH_LIKE_PHOTO}\"")
            buildConfigField("String","FIRST_PATH_UNLIKE_PHOTO", "\"${FIRST_PATH_UNLIKE_PHOTO}\"")
            buildConfigField("String","SECOND_PATH_UNLIKE_PHOTO", "\"${SECOND_PATH_UNLIKE_PHOTO}\"")
            buildConfigField("String","PATH_PHOTO", "\"${PATH_PHOTO}\"")

            buildConfigField("String","PATH_SEARCH_PHOTOS", "\"${PATH_SEARCH_PHOTOS}\"")
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
        buildConfig = true
    }
}

dependencies {

    implementation(project(":domain"))

    // core
    implementation(libs.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // di
    implementation(libs.javax.inject)

    // data
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.rxjava)
    implementation(libs.okhttp3.interceptor)
    implementation(libs.gson)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)
    implementation(libs.datastore)
    implementation(libs.appauth)

    // tests
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
}