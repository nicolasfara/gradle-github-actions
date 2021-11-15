package gghactions.functional

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.string.shouldContain
import org.gradle.testkit.runner.GradleRunner
import java.io.File

/**
 * A simple functional test for the 'gghactions.greeting' plugin.
 */
class GradleGithubActionsPluginFunctionalTest : WordSpec({
    val projectDir = File("build/functionalTest")
    fun setupTest() {
        // Setup the test build
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle.kts").writeText("")
        projectDir.resolve("build.gradle.kts").writeText(
            """
                import gghactions.model.github.Gradle
                import gghactions.model.github.Cli
                
                plugins {
                    id("gradle-github-actions")
                }
                
                githubWorkflow {
                    on = listOf("push", "pull_request")
                    build {
                        name = "Build, test and check"
                        steps = listOf(
                            Gradle(name = "Check", tasks = listOf("check")),
                            Cli(name = "Echo", run = "echo hello world")
                        )
                    }
                }
            """.trimIndent()
        )
    }

    "The apply of the plugin" should {
        "greet" {
            setupTest()
            // Run the build
            val result = GradleRunner.create()
                .forwardOutput()
                .withPluginClasspath()
                .withArguments("githubWorkflowGenerate")
                .withProjectDir(projectDir)
                .build()

            // Verify the result
            result.output shouldContain "test"
        }
    }
})
