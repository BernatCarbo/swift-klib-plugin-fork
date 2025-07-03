plugins {
    id("java-gradle-plugin")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.publish)
}

dependencies {
    implementation(gradleApi())
    implementation(libs.plugin.kotlin)

    testImplementation(gradleTestKit())
    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.test.kotest.assertions)
    testRuntimeOnly(libs.test.junit.jupiter.launcher)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

version = "1.0.0"
group = "io.github.bernatcarbo"

kotlin {
    jvmToolchain(17)
}

@Suppress("UnstableApiUsage")
gradlePlugin {
    website = "https://github.com/bernatcarbo/swift-klib-plugin-fork"
    vcsUrl = "https://github.com/bernatcarbo/swift-klib-plugin-fork"

    plugins {
        create("swiftklibfork") {
            id = "io.github.bernatcarbo.swiftklibfork"
            displayName = "SwiftKlib Gradle Plugin"
            description = "Gradle Plugin to inject Swift-code for Kotlin Multiplatform iOS target"
            implementationClass = "io.github.ttypic.swiftklib.gradle.SwiftKlibPlugin"
            tags = listOf("kotlin-multiplatform", "swift")
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/bernatcarbo/swift-klib-plugin-fork")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
