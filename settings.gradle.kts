pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        google()
        jcenter() // Deprecated, might be better to avoid
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(url = "https://jitpack.io") // Correct Kotlin DSL syntax for custom Maven repositories
        google()
        jcenter() // Deprecated, but still works if needed
    }
}

rootProject.name = "Misa"
include(":app")
