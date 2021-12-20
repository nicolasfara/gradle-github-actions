@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`
    jacoco
    `maven-publish`
    signing

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.publish)
    alias(libs.plugins.ktlint)
}

group = "it.nicolasfarabegoli"
description = "Simple plugin to configure github workflows"
version = properties["version"] as String

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
        displayName = "Github workflow plugin configurator"
        description = "A simple plugin useful to configure a Github workflow inside the gradle file"
        id = "$group.${project.name}"
        implementationClass = "gghactions.GithubActionsPlugin"
    }
}

pluginBundle {
    website = "https://github.com/nicolasfara/gradle-github-actions"
    vcsUrl = "https://github.com/nicolasfara/gradle-github-actions"
    tags = listOf("github-actions", "ci")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        withType<MavenPublication> {
            version = "${project.properties["version"]}"
            artifact(javadocJar)

            pom {
                name.set("gradle-github-actions")
                description.set("A simple plugin useful to configure a Github workflow inside the gradle file")
                url.set("https://github.com/nicolasfara/gradle-github-actions")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("nicolasfara")
                        name.set("Nicolas Farabegoli")
                        email.set("nicolas.farabegoli@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/nicolasfara/gradle-github-actions")
                }
            }
        }
    }
    repositories {
        maven {
            name = "sonatype"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("GPG_KEY"),
        System.getenv("GPG_PASSPHRASE")
    )
    sign(publishing.publications)
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
