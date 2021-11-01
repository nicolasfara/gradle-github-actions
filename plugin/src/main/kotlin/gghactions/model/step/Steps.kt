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
        steps += ActionStep(actionName)
    }
    fun action(actionName: String, init: ActionStep.() -> Unit) = initStep(ActionStep(actionName), init)
    fun gradle(init: GradleCommand.() -> Unit) = initStep(GradleCommand(), init)
    fun command(init: Command.() -> Unit) = initStep(Command(), init)
}

// Delete this!!!!
fun steps(init: WorkflowSteps.() -> Unit): WorkflowSteps {
    val steps = WorkflowSteps()
    steps.init()
    return steps
}

fun main() {
    val stps = steps {
        gradle {
            name = ""
        }
        command {
            run = ""
        }
        action("gfdg")
        action("") {
            env += "CODECO_TOKEN" to "gsfgsdfgst53qebv"
            env += "OTHER_TOKEN" to "JRfjdoekgj"
        }
    }
    print(stps.toString())
}
