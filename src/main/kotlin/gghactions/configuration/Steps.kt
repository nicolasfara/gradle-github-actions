package gghactions.configuration

import gghactions.model.Action
import gghactions.model.Cli
import gghactions.model.Step

abstract class StepsConf {
    internal val userSteps = arrayListOf<Step>()

    fun <T : Step> initStep(step: T, config: T.() -> Unit): T {
        step.config()
        userSteps.add(step)
        return step
    }
}

class Steps : StepsConf() {
    fun cli(init: Cli.() -> Unit) = initStep(Cli(name = "Changeme", run = "changeme"), init)
    fun action(init: Action.() -> Unit) = initStep(Action(name = "Changeme", uses = "changeme"), init)
}