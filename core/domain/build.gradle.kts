plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.biprangshu.subtracker.core.domain"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Minimal dependencies - domain should be as pure as possible
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    // Compose runtime only for @Immutable annotation
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)

    // Coroutines (for Flow in repository interfaces)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Hilt for @Inject on use cases
    implementation(libs.hilt.android)
}
