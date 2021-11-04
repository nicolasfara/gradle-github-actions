package gghactions

import gghactions.model.step.WorkflowSteps
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * A simple 'hello world' plugin.
 */
class GradleGithubActionsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("githubWorkflow", GradleGithubActionsExtension::class.java, project)
        // Register a task
        project.tasks.register("githubWorkflowGenerate", GradleGithubActionsTask::class.java).get().run {
            file.set(extension.defaultAction)
        }
    }
}

open class GradleGithubActionsExtension(project: Project) {
    val defaultAction: Property<String> = project.objects.property(String::class.java)
        .apply { convention("empty action...") }

    fun steps(init: WorkflowSteps.() -> Unit) {
        val steps = WorkflowSteps()
        steps.init()
        defaultAction.set(steps.toString())
    }
}

open class GradleGithubActionsTask : DefaultTask() {

    @Input
    val file: Property<String> = project.objects.property(String::class.java)

    @TaskAction
    fun generateActions() {
        logger.quiet(file.get())
    }
}
