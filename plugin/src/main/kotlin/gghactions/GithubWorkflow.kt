package gghactions

import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import kotlin.reflect.full.createInstance

/**
 * Represent the workflow.
 */
class GithubWorkflow(project: Project) {
    /**
     * On which branch the publish step should run.
     */
    val targetBranch: ListProperty<String> = project.objects.listProperty(String::class.java)

    /**
     * Name of the workflow.
     */
    var name: String = "Continuous Integration"

    /**
     * Build step of the workflow.
     */
    var build: Job? = null

    /**
     * Publish step of the workflow.
     */
    var publish: Job? = null
}

/**
 * A build step.
 */
class Job(project: Project) {
    /**
     * Name of the step.
     */
    var name: String = ""

    /**
     * List of steps that form the build.
     */
    val steps: ListProperty<Step> = project.objects.listProperty(Step::class.java)
        .apply { convention(emptyList()) }

    /**
     * [config] DSL lambda to build a gradle step.
     */
    fun gradle(config: GradleStep.() -> Unit) = genericStep(config)

    /**
     * [config] DSL lambda to build a CLI step.
     */
    fun cli(config: CliStep.() -> Unit) = genericStep(config)

    /**
     * [config] DSL lambda to build an action step.
     */
    fun action(config: ActionStep.() -> Unit) = genericStep(config)

    private inline fun <reified T : Step> genericStep(config: T.() -> Unit) {
        val step = T::class.createInstance().apply(config)
        steps.set(steps.get() + step)
    }
}

/**
 * Interface for a single step of the workflow.
 */
interface Step {
    /**
     * Name of the step.
     */
    var name: String

    /**
     * Environment variable of the step.
     */
    var env: Map<String, String>?

    /**
     * Condition for running this step.
     */
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
 *         runStep("cmd") {
 *           """
 *           git status
 *           """
 *         }
 *     }
 * }
 * ```
 */
class GradleStep : Step {
    override lateinit var name: String
    override var env: Map<String, String>? = null
    override var condition: String? = null

    /**
     * List of gradle tasks.
     */
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

    /**
     * The CLI command to run on the step.
     */
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

    /**
     * Name of the action to use in the format <name>/<action>@<version>.
     */
    lateinit var actionName: String

    /**
     * Parameters used by the action.
     */
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
