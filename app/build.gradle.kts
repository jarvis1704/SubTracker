
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.dagger.hilt.android")
//    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.baselineprofile)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.biprangshu.subtracker"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.biprangshu.subtracker"
        minSdk = 24
        targetSdk = 36
        versionCode = 8
        versionName = "1.7 (play)"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug{
            isMinifyEnabled=false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.ui)
    implementation(libs.navigation3.runtime)
    implementation(libs.navigation3.ui)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.compose.animation.core)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.firebase.ai)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    "baselineProfile"(project(":baselineprofile"))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.navigation3.ui)

    implementation(libs.androidx.navigation3.compose)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)

    //extended icons
    implementation(libs.androidx.compose.material.icons.extended)

    // Credential Manager
    implementation (libs.androidx.credentials)
    implementation (libs.androidx.credentials.play.services.auth)
    implementation (libs.googleid)

    implementation (libs.play.services.auth)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    ksp(libs.androidx.lifecycle.compiler)

    implementation(libs.lottie)
    implementation (libs.lottie.compose)


    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.okhttp.bom)) // Add OkHttp BOM
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.coil.compose)

    implementation(libs.vico.compose.m3)

    implementation(libs.androidx.core.splashscreen)

    implementation(platform("com.google.firebase:firebase-bom:34.8.0"))

    implementation("com.google.firebase:firebase-analytics")
}