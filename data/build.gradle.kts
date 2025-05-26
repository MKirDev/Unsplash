import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
}

val UNSPLASH_AUTH_BASE_URI: String = gradleLocalProperties(rootDir).getProperty("UNSPLASH_AUTH_BASE_URI")
val TOKEN_URI: String = gradleLocalProperties(rootDir).getProperty("TOKEN_URI")
val CLIENT_ID: String = gradleLocalProperties(rootDir).getProperty("CLIENT_ID")
val CLIENT_SECRET: String = gradleLocalProperties(rootDir).getProperty("CLIENT_SECRET")
val REDIRECT_URI: String = gradleLocalProperties(rootDir).getProperty("REDIRECT_URI")
val GRANT_TYPE: String = gradleLocalProperties(rootDir).getProperty("GRANT_TYPE")
val SCOPE: String = gradleLocalProperties(rootDir).getProperty("SCOPE")

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
    implementation(libs.okhttp3.interceptor)
    implementation(libs.gson)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
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