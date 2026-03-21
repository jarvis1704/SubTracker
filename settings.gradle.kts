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
    }
}

rootProject.name = "SubTracker"
include(":app")
include(":baselineprofile")
include(":core:domain")
include(":core:common")
include(":core:data")
include(":core:ui")
include(":core:navigation")
include(":core:worker")
include(":feature:home")
include(":feature:analytics")
include(":feature:settings")
include(":feature:subscriptions")
include(":feature:onboarding")
