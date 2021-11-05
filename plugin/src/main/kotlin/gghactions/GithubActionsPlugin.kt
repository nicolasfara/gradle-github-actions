package gghactions

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.lang.IllegalStateException

private const val EXTENSION_NAME = "githubWorkflow"

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
open class GithubActionsPlugin : Plugin<Project> {

    companion object {
        inline fun <reified T> Project.createExtension(name: String, vararg args: Any?): T =
            project.extensions.create(name, T::class.java, args)
    }

    override fun apply(project: Project) {
        val extension = project.createExtension<GithubActionsExtension>(
            EXTENSION_NAME,
            project
        )
        // Register a task
        project.tasks.register(
            "githubWorkflowGenerate",
            GithubActionsTask::class.java
        )
    }
}

internal fun Project.githubActions(): GithubActionsExtension =
    extensions.getByName(EXTENSION_NAME) as? GithubActionsExtension
        ?: throw IllegalStateException("$EXTENSION_NAME is not of the correct type")
