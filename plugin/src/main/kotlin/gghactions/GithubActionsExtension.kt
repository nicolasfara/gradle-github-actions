package gghactions

import gghactions.model.*
import org.gradle.api.Project

/**
 * Container for the GitHub actions plugin configuration DSL.
 * [project] is the Gradle project.
 */
open class GithubActionsExtension(private val project: Project) {

    internal val workflowConfiguration: Workflow = Workflow(
        name = "Test",
        on = mapOf(
            "push" to mapOf("tags" to "*", "branches-ignore" to listOf("renovate/**", "renovate/**")),
            "pull_request" to "",
            "workflow_dispatch" to ""
        ),
        jobs = Job(
            build = JobId(
                runsOn = listOf("ubuntu-latest"),
                steps = listOf(
                    Cli(name = "Compile", run = "./gradlew check"),
                    Action(name = "Some action", uses = "foo/bar@v2", with = mapOf("FOO" to "bar"))
                )
            )
        )
    )

}
