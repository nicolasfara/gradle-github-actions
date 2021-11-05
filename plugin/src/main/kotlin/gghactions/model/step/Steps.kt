package gghactions.model.step

import gghactions.model.Element

abstract class Steps : Element {
    var steps = emptySequence<Step>()

    protected fun <T : Step> initStep(stp: T, init: T.() -> Unit): T {
        stp.init()
        steps += stp
        return stp
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.appendLine("${indent}steps:")
        for (step in steps) {
            step.render(builder, "$indent  ")
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }
}

class WorkflowSteps : Steps() {
    fun action(actionName: String) {
        steps += ActionStepOld(actionName)
    }
    fun action(actionName: String, init: ActionStepOld.() -> Unit) = initStep(ActionStepOld(actionName), init)
    fun gradle(init: GradleCommand.() -> Unit) = initStep(GradleCommand(), init)
    fun command(init: Command.() -> Unit) = initStep(Command(), init)
}
