plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`
    jacoco

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.5.31"

    // Other plugins
    id("com.gradle.plugin-publish") version "0.16.0"
    id("pl.droidsonroids.jacoco.testkit") version "1.0.9"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
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

val kotestVersion = "5.0.0.M3"

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion") // for kotest core assertions
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion") // for kotest core jvm assertions
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.1")
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
    config = files(File(projectDir, "config/detekt.yml"))
}
