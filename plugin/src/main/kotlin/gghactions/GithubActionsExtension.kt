package gghactions

import gghactions.model.github.Job
import gghactions.model.github.Workflow
import org.gradle.api.Project

/**
 * Container for the GitHub actions plugin configuration DSL.
 * [project] is the Gradle project.
 */
open class GithubActionsExtension(private val project: Project) {

    internal val workflowConfiguration: Workflow = Workflow(name = "Continuous Integration", on = emptyList(), jobs = emptyList())

    /**
     * The name of the CI workflow.
     */
    var name: String
        get() = workflowConfiguration.name
        set(value) { workflowConfiguration.name = value }

    var on: List<String>
        get() = workflowConfiguration.on
        set(value) { workflowConfiguration.on = value }

    /**
     * [config] is the trailing lambda for creating a build object.
     */
    fun build(config: Job.() -> Unit = { }) {
        val buildJob = Job(name = "Build check and test").apply(config)
        workflowConfiguration.jobs += buildJob
    }

    /**
     * [config] is the trailing lambda for creating the publish step.
     */
    fun publish(config: Job.() -> Unit = { }) {
        val publishJob = Job(name = "Publish").apply(config)
        workflowConfiguration.jobs += publishJob
    }
}
