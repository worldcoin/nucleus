plugins {
    kotlin("jvm") version "2.0.21"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    `maven-publish`
}

group = "com.worldcoin"
version = "__VERSION__"

val artifactId = "nucleus"

repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly("androidx.compose.ui:ui:1.7.6")
    compileOnly("androidx.compose.ui:ui-text:1.7.6")
    compileOnly("androidx.compose.runtime:runtime:1.7.6")
    compileOnly("androidx.compose.ui:ui-unit:1.7.6")
    compileOnly("androidx.compose.ui:ui-graphics:1.7.6")
    compileOnly("androidx.compose.foundation:foundation:1.7.6")
}

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.worldcoin"
            this.artifactId = artifactId
            this.version = project.version.toString()
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/worldcoin/nucleus")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
