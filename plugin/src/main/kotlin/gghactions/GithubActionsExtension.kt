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

    @JvmOverloads fun build(
        config: Build.() -> Unit = { }
    ) {
        println(name)
        val build = Build(project).apply(config)
        configuration.build.set(build)
    }
}
