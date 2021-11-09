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
    var env: Map<String, String>?
    var condition: String?
}

private fun printMap(map: Map<String, String>, name: String, sb: StringBuilder) {
    sb.appendLine("  $name:")
    map.forEach { (k, v) -> sb.appendLine("    $k: $v") }
}

class GradleStep : Step {
    override lateinit var name: String
    override var env: Map<String, String>? = null
    override var condition: String? = null
    lateinit var tasks: List<String>

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine("- name: $name")
        condition?.let { stringBuilder.appendLine("  if: $it") }
        stringBuilder.appendLine("  run: ./gradlew ${tasks.joinToString(" ")}")
        env?.let { printMap(it, "env", stringBuilder) }
        return stringBuilder.toString()
    }
}

class CliStep : Step {
    override lateinit var name: String
    override var env: Map<String, String>? = null
    override var condition: String? = null
    lateinit var run: String

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("- name: $name")
        condition?.let { stringBuilder.append("  if: $it") }
        stringBuilder.append("  run: $run")
        env?.let { printMap(it, "env", stringBuilder) }
        return stringBuilder.toString()
    }
}

class ActionStep : Step {
    override lateinit var name: String
    override var env: Map<String, String>? = null
    override var condition: String? = null
    lateinit var actionName: String
    var with: Map<String, String>? = null

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("- name: $name")
        condition?.let { stringBuilder.append("  if: $it") }
        stringBuilder.append("  uses: $actionName")
        env?.let { printMap(it, "env", stringBuilder) }
        with?.let { printMap(it, "with", stringBuilder) }
        return stringBuilder.toString()
    }
}
