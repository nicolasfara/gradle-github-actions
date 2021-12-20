package gghactions.configuration

import gghactions.model.Action
import gghactions.model.Cli
import gghactions.model.Step

/**
 * DSL class used to build a step in the workflow.
 */
class Steps {
    internal val userSteps = arrayListOf<Step>()

    private fun <T : Step> initStep(step: T, config: T.() -> Unit): T {
        step.config()
        userSteps.add(step)
        return step
    }

    /**
     * A command line command in the job.
     *
     * ```
     * build {
     *   cli {
     *     name = "Some task"
     *     run = "echo hello world"
     *   }
     * }
     * ```
     *
     * Refer to [Cli] class for a more detailed configuration options.
     */
    fun cli(init: Cli.() -> Unit) = initStep(Cli(name = "Changeme", run = "changeme"), init)

    /**
     * Use of an action as a step in the job.
     *
     * ```
     * build {
     *   action {
     *     name = "Some action"
     *     uses = "foo/bar@v1"
     *   }
     * }
     * ```
     *
     * Refer to [Action] class for a more detailed configuration option.
     */
    fun action(init: Action.() -> Unit) = initStep(Action(name = "Changeme", uses = "changeme"), init)
}
