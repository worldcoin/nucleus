pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/worldcoin/nucleus")

            val githubPackagesUser =
                providers.gradleProperty("githubPackagesUser").orNull
                    ?: System.getenv("GITHUB_USER")
                    ?: System.getenv("GITHUB_ACTOR")
            val githubPackagesToken =
                providers.gradleProperty("githubPackagesToken").orNull
                    ?: System.getenv("GITHUB_TOKEN")

            if (githubPackagesUser != null && githubPackagesToken != null) {
                credentials {
                    username = githubPackagesUser
                    password = githubPackagesToken
                }
            }
        }
    }
}

rootProject.name = "NucleusApp"
include(":app", ":nucleus")
