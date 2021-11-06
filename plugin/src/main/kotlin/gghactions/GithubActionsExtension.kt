package gghactions

import org.gradle.api.Project

/**
 * Container for the GitHub actions plugin configuration DSL.
 */
open class GithubActionsExtension(val project: Project) {

    internal val configuration = GithubWorkflow(project)

    var name: String
        get() = configuration.name.get()
        set(value) = configuration.name.set(value)

    @JvmOverloads fun build(config: Build.() -> Unit = { }) {
        val build = Build(project).apply(config)
        configuration.build.set(build)
    }

    @JvmOverloads fun publish(config: Build.() -> Unit = { }) {
        val publish = Build(project).apply(config)
        configuration.publish.set(publish)
    }
}
