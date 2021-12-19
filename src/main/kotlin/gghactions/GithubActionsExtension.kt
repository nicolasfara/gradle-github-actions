package gghactions

import gghactions.configuration.Steps
import gghactions.model.Workflow
import org.gradle.api.Project

/**
 * Container for the GitHub actions plugin configuration DSL.
 * [project] is the Gradle project.
 */
open class GithubActionsExtension(private val project: Project) {

    internal val workflow = Workflow.defaultConfig()

    var name: String
        get() = workflow.name
        set(value) { workflow.name = value }

    var on: Map<String, Any>
        get() = workflow.on
        set(value) { workflow.on = value }

    var os: List<String>
        get() = workflow.jobs.build.strategy.matrix["os"] as List<String>
        set(value) { workflow.jobs.build.strategy.matrix["os"] = value }

    var java: List<Int>
        get() = workflow.jobs.build.strategy.matrix["java-version"] as List<Int>
        set(value) { workflow.jobs.build.strategy.matrix["java-version"] = value }

    fun build(config: Steps.() -> Unit) {
        val steps = Steps()
        steps.config()
        workflow.jobs.build.steps += steps.userSteps
    }
}
