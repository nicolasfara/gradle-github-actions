package gghactions

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class GithubActionsTask : DefaultTask() {
    init {
        group = "githubActions"
    }

    /**
     * .
     */
    @TaskAction
    fun doAction() {
        logger.quiet(project.githubActions().instances.getByName("").steps.joinToString("\n"))
    }
}
