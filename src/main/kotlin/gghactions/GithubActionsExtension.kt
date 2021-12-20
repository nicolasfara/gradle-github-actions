package gghactions

import gghactions.configuration.Steps
import gghactions.model.Workflow
import org.gradle.api.Project

/**
 * Container for the GitHub actions plugin configuration DSL.
 * [project] is the Gradle project.
 */
open class GithubActionsExtension(val project: Project) {

    internal val workflow = Workflow.defaultConfig()

    /**
     * The workflow's name.
     */
    var name: String
        get() = workflow.name
        set(value) { workflow.name = value }

    /**
     * The trigger policy for this workflow.
     */
    var on: Map<String, Any>
        get() = workflow.on
        set(value) { workflow.on = value }

    /**
     * Set up the Os types used in the workflow.
     * The default available os are: `ubuntu-latest`, `macos-latest`, `windows-latest`.
     */
    @Suppress("UNCHECKED_CAST")
    var os: List<String>
        get() = workflow.jobs.build.strategy.matrix["os"] as List<String>
        set(value) { workflow.jobs.build.strategy.matrix["os"] = value }

    /**
     * Set up the Java version used in the workflow.
     * The version should be compatible with the version accepted by the action: `actions/setup-java`.
     */
    @Suppress("UNCHECKED_CAST")
    var java: List<Int>
        get() = workflow.jobs.build.strategy.matrix["java-version"] as List<Int>
        set(value) { workflow.jobs.build.strategy.matrix["java-version"] = value }

    /**
     * Configure the build step of the workflow.
     * This step allows adding more steps in the building job.
     */
    fun build(config: Steps.() -> Unit) {
        val steps = Steps()
        steps.config()
        workflow.jobs.build.steps += steps.userSteps
    }
}
