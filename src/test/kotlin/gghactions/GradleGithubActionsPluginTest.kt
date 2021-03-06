package gghactions

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldNotBe
import org.gradle.testfixtures.ProjectBuilder

/**
 * A simple unit test for the plugin.
 */
class GradleGithubActionsPluginTest : WordSpec({

    "The plugin" should {
        "be registered" {
            val project = ProjectBuilder.builder().build()
            project.plugins.apply("it.nicolasfarabegoli.gradle-github-actions")
            // Verify the result
            project.tasks.findByName("githubWorkflowGenerate") shouldNotBe null
        }
    }
})
