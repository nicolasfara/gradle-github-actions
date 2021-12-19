package gghactions.model

/**
 * TODO.
 * [name] TODO.
 * [on] TODO.
 * [jobs] TODO.
 */
data class Workflow(
    var name: String,
    var on: Map<String, Any>,
    var jobs: Job
) {
    companion object {
        fun defaultConfig(): Workflow {
            return Workflow(
                name = "Build and Test",
                on = mapOf(
                    "push" to "",
                    "pull_request" to ""
                ),
                jobs = Job(
                    build = JobId(
                        runsOn = "\${{ matrix.os }}",
                        steps = listOf(
                            Action(name = "Checkout", uses = "actions/checkout@v2.4.0"),
                            Action(name = "Setup Java", uses = "actions/setup-java@2.4.0", with = mapOf(
                                "distribution" to "adopt",
                                "java-version" to "\${{ matrix.java-version }}"
                            )),
                            Action(name = "Gradle cache", uses = "actions/cache@2.1.7", with = mapOf(
                                "key" to "\${{ runner.os }}-gradle-wrapper-\${{ hashFiles('gradle/wrapper/gradle-wrapper.properties') }}",
                                "path" to """
                                    ~/.gradle/caches
                                    ~/.gradle/wrapper
                                """.trimIndent()
                            )),
                            Cli(name = "Build project", run = "./gradlew build")
                        )
                    )
                )
            )
        }
    }
}
