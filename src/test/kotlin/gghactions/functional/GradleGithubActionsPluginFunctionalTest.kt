package gghactions.functional

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.GradleRunner
import java.io.File
import java.io.File.separator as SEP

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
                import gghactions.model.*
                plugins {
                    id("gradle-github-actions")
                }
                
                githubWorkflow {
                    os = listOf("ubuntu-latest", "macos-latest")
                    build {
                        cli { name = "Hello world"; run = "echo hello world" }
                    }
                }
            """.trimIndent()
        )
    }

    "The apply of the plugin" should {
        "greet" {
            setupTest()
            // Run the build
            GradleRunner.create()
                .forwardOutput()
                .withPluginClasspath()
                .withArguments("githubWorkflowGenerate")
                .withProjectDir(projectDir)
                .build()

            // Verify the result
            File("${projectDir.path}$SEP.github${SEP}workflows${SEP}build-publish.yml").exists() shouldBe true
        }
    }
})
