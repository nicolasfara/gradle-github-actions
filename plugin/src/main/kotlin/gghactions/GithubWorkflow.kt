package gghactions

import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

private inline fun <reified T> Project.propertyWithDefault(default: T): Property<T> =
    objects.property(T::class.java).apply { convention(default) }

private inline fun <reified T> Project.property(): Property<T> =
    objects.property(T::class.java)

class GithubWorkflow(project: Project) {
    val targetBranch: ListProperty<String> = project.objects.listProperty(String::class.java)
    val name: Property<String> = project.propertyWithDefault("Continuous Integration")
    val build: Property<Build> = project.property()
    val publish: Property<Build> = project.property()
}

class Build(project: Project) {
    var name: String = ""
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

/**
 * Interface for a single step of the workflow.
 */
interface Step {
    var name: String
    var env: Map<String, String>?
    var condition: String?
}

private fun printMap(map: Map<String, String>, name: String, sb: StringBuilder) {
    sb.appendLine("  $name:")
    map.forEach { (k, v) -> sb.appendLine("    $k: $v") }
}

/**
 * Represent a list of gradle commands in the workflow.
 *
 * ```kotlin
 * githubWorkflow {
 *     build {
 *         gradle {
 *             name = "Test and check"
 *             tasks = listOf("test", "check")
 *         }
 *     }
 * }
 * ```
 */
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

/**
 * Represent a CLI command in the workflow.
 *
 * ```kotlin
 * githubWorkflow {
 *     build {
 *         cli {
 *             name = "Echo an hello world"
 *             run = "echo hello world"
 *         }
 *     }
 * }
 * ```
 */
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

/**
 * Represent the use of an action in the workflow.
 *
 * ```kotlin
 * githubWorkflow {
 *     build {
 *         action {
 *             actionName = "actions/cache@v2"
 *             env = mapOf("foo" to "bar")
 *         }
 *     }
 * }
 * ```
 */
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
