package gghactions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Plugin to create a GitHub actions workflow.
 *
 * ```kotlin
 * githubWorkflow {
 *      steps {
 *          command {
 *              run = "echo hello world"
 *          }
 *      }
 * }
 * ```
 */
class GithubActionsPlugin : Plugin<Project> {

    companion object {
        private const val EXTENSION_NAME = "githubWorkflow"
        private inline fun <reified T> Project.createExtension(name: String, vararg args: Any?): T =
            project.extensions.create(name, T::class.java, *args)

        private inline fun <reified T : Task> Project.registerTask(name: String): T =
            project.tasks.register(name, T::class.java).get()
    }

    override fun apply(project: Project) {
        val extension = project.createExtension<GithubActionsExtension>(EXTENSION_NAME, project)
        // Register a task
        project.registerTask<GithubActionsTask>("githubWorkflowGenerate").run {
            workflow.set(extension.configuration)
        }
    }
}
