plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.worldcoin.nucleus"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    sourceSets {
        getByName("main").java.srcDir("../../../build/android/src/main/kotlin")
    }
}

dependencies {
    api("androidx.compose.ui:ui-graphics:1.7.6")
}
