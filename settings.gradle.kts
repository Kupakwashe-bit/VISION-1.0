pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KUONA"

include(":app")
include(":core:model")
include(":core:data")
include(":core:network")
include(":core:ml")
include(":core:render")
include(":core:audio")
include(":feature:scan")
include(":feature:analyst")
include(":feature:devices")
include(":feature:reports")
include(":feature:settings")
