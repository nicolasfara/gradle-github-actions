package gghactions

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class GithubActionsTask : DefaultTask() {
    @Input
    val workflow: Property<GithubWorkflow> = project.objects.property(GithubWorkflow::class.java)

    /**
     * .
     */
    @TaskAction
    fun doAction() {
        logger.quiet(workflow.get().name.get())
        logger.quiet(workflow.get().build.get().steps.get().toString())
    }
}
