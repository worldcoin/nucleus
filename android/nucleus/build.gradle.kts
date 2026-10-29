import groovy.json.JsonSlurper
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Locale
import javax.inject.Inject

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
    `maven-publish`
}

abstract class GenerateFontResources : DefaultTask() {
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val fontDirectory: DirectoryProperty

    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val definitionsFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @get:Inject
    abstract val fileSystemOperations: FileSystemOperations

    @TaskAction
    fun generate() {
        val definitions = JsonSlurper().parse(definitionsFile.get().asFile) as Map<*, *>
        val families = definitions["families"] as Map<*, *>
        val filenames = families.values.map { (it as Map<*, *>)["file"] as String }
        filenames.forEach { filename ->
            require(
                fontDirectory
                    .file(filename)
                    .get()
                    .asFile.isFile,
            ) {
                "Missing font source: $filename"
            }
        }
        fileSystemOperations.sync {
            into(outputDirectory)
            from(fontDirectory) {
                include(filenames)
                into("font")
                // Match snakeCase() in tokens/formats/shared.ts.
                rename { filename ->
                    filename
                        .substringBeforeLast('.')
                        .replace(Regex("([a-z0-9])([A-Z])"), "$1_$2")
                        .lowercase(Locale.ROOT) + ".ttf"
                }
            }
        }
    }
}

val generateFontResources by tasks.registering(GenerateFontResources::class) {
    fontDirectory.set(layout.projectDirectory.dir("../../fonts"))
    definitionsFile.set(layout.projectDirectory.file("../../tokens/definitions/font/fonts.json"))
    outputDirectory.set(layout.buildDirectory.dir("generated/fontResources/res"))
}

androidComponents {
    onVariants { variant ->
        variant.sources.res?.addGeneratedSourceDirectory(
            generateFontResources,
            GenerateFontResources::outputDirectory,
        )
    }
}

group = "com.worldcoin"
version =
    rootProject.layout.projectDirectory
        .file("../VERSION")
        .asFile
        .readText()
        .trim()

android {
    namespace = "com.worldcoin.nucleus"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.worldcoin"
                artifactId = "nucleus"
                version = project.version.toString()
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
}
