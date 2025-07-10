plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.gradle.publish) apply false
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "io.github.bernatcarbo"

nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId = "io.github.bernatcarbo--5eccc0c4-ded7-496a-82dc-89a86f40e182"
            nexusUrl = uri("https://ossrh-staging-api.central.sonatype.com/service/local/")
            username = System.getenv("MAVEN_USERNAME")
            password = System.getenv("MAVEN_TOKEN")
        }
    }
}
