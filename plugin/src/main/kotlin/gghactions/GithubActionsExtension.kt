package gghactions

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.provider.Property

private inline fun <reified T> Project.propertyWithDefault(default: T): Property<T> =
    objects.property(T::class.java).apply { convention(default) }

internal class WorkflowConfiguration(project: Project) {
    val name: Property<String> = project.propertyWithDefault("Continuous Integration")
    val on: Property<Any?> = TODO("Choose a proper type")
    val build: Property<Any?> = TODO("Choose a proper representation for the steps")
}

/**
 * Container for the GitHub actions plugin configuration DSL.
 */
open class GithubActionsExtension(project: Project) {
    /**
     * .
     */
    val instances: NamedDomainObjectContainer<WorkflowStepsConfiguration> =
        project.container(WorkflowStepsConfiguration::class.java) {
            WorkflowStepsConfiguration(project)
        }

    /**
     * .
     */
    fun steps(config: WorkflowStepsConfiguration.() -> Unit = { }) {
        instances.configure(object : Closure<Unit>(this, this) {
            fun doCall() {
                (delegate as? WorkflowStepsConfiguration)?.let {
                    config(it)
                }
            }
        })
    }
}
