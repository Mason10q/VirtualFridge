import buildsrc.Libs
import buildsrc.Versions

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "ru.virtual.virtualfridge"
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "ru.virtualapps.virtualfridge"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = Versions.compatibility
        targetCompatibility = Versions.compatibility
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":core-network"))
    implementation(project(":core-navigation"))
    implementation(project(":core-android"))
    implementation(project(":core-res"))
    implementation(project(":core-db"))
    implementation(project(":feature-product-list"))

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.Google.material)
    implementation(Libs.AndroidX.constraintlayout)

    implementation(Libs.AndroidX.navigationUiKtx)
    implementation(Libs.AndroidX.navigationFragmentKtx)
}