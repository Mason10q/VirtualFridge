import buildsrc.Versions
import buildsrc.Libs

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "ru.virtual.feature_recipes"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(project(":core-android"))
    implementation(project(":core-res"))
    implementation(project(":core-navigation"))

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.fragments)
    implementation(Libs.Google.material)

    implementation(Libs.RxJava.rxJava)
    implementation(Libs.RxJava.rxJavaAndroid)

    implementation(Libs.DI.dagger)
    kapt(Libs.DI.daggerCompiler)

    implementation(Libs.AndroidX.navigationUiKtx)
    implementation(Libs.AndroidX.navigationFragmentKtx)
}