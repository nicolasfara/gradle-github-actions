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
                plugins {
                    id("gradle-github-actions")
                }
                
                githubWorkflow {
                    build {
                        gradle {
                            name = "Some name"
                            tasks = listOf("test", "check")
                        }
                    }
                }
            """.trimIndent()
        )
    }

    "The apply of the plugin" should {
        "greet" {
            setupTest()
            // Run the build
            val runner = GradleRunner.create()
            runner.forwardOutput()
            runner.withPluginClasspath()
            runner.withArguments("githubWorkflowGenerate")
            runner.withProjectDir(projectDir)
            val result = runner.build()

            // Verify the result
            result.output shouldContain "test"
        }
    }
})
