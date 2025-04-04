/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Gradle plugin project to get you started.
 * For more details on writing Custom Plugins, please refer to https://docs.gradle.org/8.13/userguide/custom_plugins.html in the Gradle documentation.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)

    id("com.autonomousapps.dependency-analysis")
    id("com.gradle.plugin-publish") version "1.3.1"
    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use the Kotlin JUnit 5 integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    api("com.pinterest.ktlint:ktlint-rule-engine:1.5.0")
    runtimeOnly("com.pinterest.ktlint:ktlint-ruleset-standard:1.5.0")


    implementation("com.pinterest.ktlint:ktlint-cli-ruleset-core:1.5.0")
    implementation("com.pinterest.ktlint:ktlint-rule-engine-core:1.5.0")

}

group = "io.github.truepadawan"
version = "1.0.1"

gradlePlugin {
    website = "https://github.com/TruePadawan/ktlint-problems-plugin"
    vcsUrl = "https://github.com/TruePadawan/ktlint-problems-plugin"

    // Define the plugin
    plugins.register("ktlintProblemsPlugin") {
        id = "io.github.truepadawan.ktlint.problems"
        implementationClass = "io.github.truepadawan.ktlint.problems.KtlintProblemsPlugin"
        displayName = "Ktlint Gradle Problems API plugin"
        description = "This plugin reports Ktlint lint errors through Gradle's Problems API"
        tags = listOf("ktlint", "problems-api", "code-formatting", "linter")
    }
}

publishing {
    publications {
        create<MavenPublication>("ktlintProblemsPlugin") {
            groupId = project.group.toString()
            artifactId = "ktlint.problems"
            version = project.version.toString()

            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            url = uri("${System.getProperty("user.home")}/.m2/repository") // Maven local repository path
        }
    }
}

tasks.named<Test>("test") {
    // Use JUnit Jupiter for unit tests.
    useJUnitPlatform()
}
