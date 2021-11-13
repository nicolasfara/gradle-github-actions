package gghactions

import org.gradle.api.Project

/**
 * Container for the GitHub actions plugin configuration DSL.
 * [project] is the Gradle project.
 */
open class GithubActionsExtension(val project: Project) {

    internal val configuration = GithubWorkflow(project)

    /**
     * The name of the CI workflow.
     */
    var name: String
        get() = configuration.name
        set(value) { configuration.name = value }

    /**
     * [config] is the trailing lambda for creating a build object.
     */
    fun build(config: Job.() -> Unit = { }) {
        val build = Job(project).apply(config)
        configuration.build = build
    }

    /**
     * [config] is the trailing lambda for creating the publish step.
     */
    fun publish(config: Job.() -> Unit = { }) {
        val publish = Job(project).apply(config)
        configuration.publish = publish
    }
}
