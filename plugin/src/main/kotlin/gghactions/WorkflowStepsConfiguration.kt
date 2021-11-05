package gghactions

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

class WorkflowStepsConfiguration(project: Project) {
    val steps: Set<String> = emptySet()

    fun gradle(config: GradleStepContainer.() -> Unit) {
        TODO()
    }

    fun command(config: CommandStepContainer.() -> Unit) {
        TODO()
    }

    fun action(config: ActionStepContainer.() -> Unit) {
        TODO()
    }
}

internal typealias WorkflowStepsConfigurationContainer = NamedDomainObjectContainer<WorkflowStepsConfiguration>
