package gghactions

import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

private inline fun <reified T> Project.propertyWithDefault(default: T): Property<T> =
    objects.property(T::class.java).apply { convention(default) }

private inline fun <reified T> Project.property(): Property<T> =
    objects.property(T::class.java)

class GithubWorkflow(project: Project) {
    val name: Property<String> = project.propertyWithDefault("Continuous Integration")
    val build: Property<Build> = project.property()
    val publish: Property<Build> = project.property()
}

class Build(project: Project) {
    val steps: ListProperty<Step> = project.objects.listProperty(Step::class.java)
        .apply { convention(emptyList()) }

    fun gradle(config: GradleStep.() -> Unit) = genericStep(config)
    fun cli(config: CliStep.() -> Unit) = genericStep(config)
    fun action(config: ActionStep.() -> Unit) = genericStep(config)

    private inline fun <reified T : Step> genericStep(config: T.() -> Unit) {
        val step = T::class.java.getDeclaredConstructor().newInstance()
            .apply(config)
        steps.set(steps.get() + step)
    }
}

interface Step {
    var name: String
    var env: Map<String, String>
}

class GradleStep : Step {
    override var name: String = ""
    override var env: Map<String, String> = emptyMap()
    var tasks: List<String> = emptyList()

    override fun toString(): String {
        return """
            - name: $name
              run: ./gradlew ${tasks.joinToString(" ")}
              env: $env
        """.trimIndent()
    }
}

class CliStep : Step {
    override var name: String = ""
    override var env: Map<String, String> = emptyMap()
    var run: String = ""
}

class ActionStep : Step {
    override var name: String = ""
    override var env: Map<String, String> = emptyMap()
    var actionName: String = ""
}
