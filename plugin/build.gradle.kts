plugins {
    id("java-gradle-plugin")
    alias(libs.plugins.kotlin.jvm)
    `maven-publish`
    signing
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

version = "1.2.0"
group = "io.github.bernatcarbo"

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
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

val javadocJar by tasks.registering(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles java doc to jar"
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        artifact(javadocJar)
        groupId = "io.github.bernatcarbo"
        version = "${project.version}"
        pom {
            name.set("Swift Klib Gradle Plugin")
            inceptionYear.set("2024")
            description.set("Swift Klib Gradle Plugin with watchosDeviceArm64 support")
            url.set("https://github.com/BernatCarbo/swift-klib-plugin-fork")
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://github.com/ttypic/swift-klib-plugin/blob/main/LICENSE")
                }
            }
            issueManagement {
                system.set("Github")
                url.set("https://github.com/BernatCarbo/swift-klib-plugin-fork/issues")
            }
            developers {
                developer {
                    id.set("bernatcarbo")
                    name.set("bernatcarbo")
                    url.set("https://github.com/BernatCarbo/swift-klib-plugin-fork")
                }
            }
            scm {
                url.set("https://github.com/BernatCarbo/swift-klib-plugin-fork")
                connection.set("scm:git:git://github.com/BernatCarbo/swift-klib-plugin-fork.git")
                developerConnection.set("scm:git:ssh://git@github.com/BernatCarbo/swift-klib-plugin-fork.git")
            }
        }
    }

    val signingTasks = tasks.withType<Sign>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        mustRunAfter(signingTasks)
    }
}

signing {
    val signingKey = System.getenv("MAVEN_SIGN_KEY")
    val signingPassword = System.getenv("MAVEN_SIGN_PASSWORD")

    useInMemoryPgpKeys(null, signingKey, signingPassword)
    sign(publishing.publications)
}
