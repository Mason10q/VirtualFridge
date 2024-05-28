pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "VirtualFridge"
include(":app")
include(":core-navigation")
include(":core-network")
include(":core-android")
include(":feature-product-list")
include(":core-db")
include(":core-res")
include(":feature-auth")
include(":feature-settings")
include(":feature-recipes")
include(":feature-fridge")
