package gghactions.functional

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.string.shouldContain
import java.io.File
import org.gradle.testkit.runner.GradleRunner

/**
 * A simple functional test for the 'gghactions.greeting' plugin.
 */
class GradleGithubActionsPluginFunctionalTest : WordSpec({
    val projectDir = File("build/functionalTest")
    fun setupTest() {
        // Setup the test build
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle").writeText("")
        projectDir.resolve("build.gradle").writeText("""
            plugins {
                id('gghactions.greeting')
            }
        """)
    }

    "The apply of the plugin" should {
        "greet" {
            setupTest()
            // Run the build
            val runner = GradleRunner.create()
            runner.forwardOutput()
            runner.withPluginClasspath()
            runner.withArguments("greeting")
            runner.withProjectDir(projectDir)
            val result = runner.build()

            // Verify the result
            result.output shouldContain "Hello from plugin 'gghactions.greeting'"
        }
    }
})
