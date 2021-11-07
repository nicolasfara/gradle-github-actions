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
    var env: Map<String, String>
    var condition: String?
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
    override var name: String = ""
    override var env: Map<String, String> = emptyMap()
    override var condition: String? = null
    var tasks: List<String> = emptyList()

    override fun toString(): String {
        return """
            - name: $name
              run: ./gradlew ${tasks.joinToString(" ")}
              env: $env
        """.trimIndent()
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
    override var name: String = ""
    override var env: Map<String, String> = emptyMap()
    var run: String = ""
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
    override var name: String = ""
    override var env: Map<String, String> = emptyMap()
    var actionName: String = ""
}
