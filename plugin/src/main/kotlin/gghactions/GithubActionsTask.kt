package gghactions

import com.charleskorn.kaml.PolymorphismStyle
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import gghactions.model.github.Workflow
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
    val workflow: Property<Workflow> = project.objects.property(Workflow::class.java)

    /**
     * The generated workflow task.
     */
    @TaskAction
    fun doAction() {
        val config = YamlConfiguration(encodeDefaults = false)
        val workflowResult = Yaml(configuration = config).encodeToString(Workflow.serializer(), workflow.get())
        logger.quiet(workflowResult)
    }
}
