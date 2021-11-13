package gghactions

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * GithubActions generate workflow.
 */
open class GithubActionsTask : DefaultTask() {
    /**
     * A property with the workflow.
     */
    @Input
    val workflow: Property<GithubWorkflow> = project.objects.property(GithubWorkflow::class.java)

    /**
     * The generate workflow task.
     */
    @TaskAction
    fun doAction() {
        logger.quiet(workflow.get().name)
        logger.quiet(workflow.get().build?.steps.toString())
    }
}
