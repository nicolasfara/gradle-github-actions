package gghactions.configuration

import gghactions.model.Action
import gghactions.model.Cli

/**
 * Default configuration for the workflow.
 */
object DefaultValues {
    /**
     * Default Os.
     */
    const val defaultOs = "ubunut-latest"

    /**
     * Default Java version.
     */
    const val defaultJavaVersion = 17

    /**
     * Default build steps.
     */
    val defaultBuildStep = listOf(
        Action(name = "Checkout", uses = "actions/checkout@v2.4.0"),
        Action(
            name = "Setup Java", uses = "actions/setup-java@2.4.0",
            with = mapOf(
                "distribution" to "adopt",
                "java-version" to "\${{ matrix.java-version }}"
            )
        ),
        Action(
            name = "Gradle cache", uses = "actions/cache@2.1.7",
            with = mapOf(
                "key" to "${'$'}{{runner.os}}-wrapper-${'$'}{{hashFiles('gradle/wrapper/gradle-wrapper.properties')}}",
                "path" to """
                    ~/.gradle/caches
                    ~/.gradle/wrapper
                """.trimIndent()
            )
        ),
        Cli(name = "Build project", run = "./gradlew build")
    )
}
