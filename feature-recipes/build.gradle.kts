import buildsrc.Versions
import buildsrc.Libs
import java.util.Properties

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

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        buildConfigField("String", "ENDPOINT_URL", "\"${properties.getProperty("ENDPOINT_URL")}\"")
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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core-android"))
    implementation(project(":core-res"))
    implementation(project(":core-navigation"))
    implementation(project(":core-network"))

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.fragments)
    implementation(Libs.Google.material)

    implementation(Libs.RxJava.rxJava)
    implementation(Libs.RxJava.rxJavaAndroid)

    implementation(Libs.Network.retrofit)
    implementation(Libs.Network.retrofitRxJava3)
    implementation(Libs.Network.retrofitGson)
    implementation(Libs.Network.ohttp)
    implementation(Libs.Network.okhttpLogInter)
    implementation(platform(Libs.Network.okhttpBom))
    implementation(Libs.Network.gson)

    implementation(Libs.DI.dagger)
    kapt(Libs.DI.daggerCompiler)

    implementation(Libs.AndroidX.navigationUiKtx)
    implementation(Libs.AndroidX.navigationFragmentKtx)

    implementation(Libs.AndroidX.paging)
    implementation(Libs.AndroidX.pagingRxJava3)

    implementation(Libs.UI.delegateAdapter)

    implementation(Libs.UI.picasso)
}