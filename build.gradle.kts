@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`
    jacoco

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.publish)
    alias(libs.plugins.ktlint)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.jackson)
    testImplementation(libs.bundles.kotest)
    detektPlugins(libs.detekt)
}

gradlePlugin {
    // Define the plugin
    val plugin by plugins.creating {
        id = "gradle-github-actions"
        implementationClass = "gghactions.GithubActionsPlugin"
    }
}

tasks.withType<Test> {
    useJUnitPlatform() // Use JUnit 5 engine
    testLogging.showStandardStreams = true
    testLogging {
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

// Disable JaCoCo on Windows, see: https://issueexplorer.com/issue/koral--/jacoco-gradle-testkit-plugin/9
tasks.jacocoTestCoverageVerification {
    enabled = !org.apache.tools.ant.taskdefs.condition.Os.isFamily(
        org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS
    )
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    config = files("./detekt.yml")
}
