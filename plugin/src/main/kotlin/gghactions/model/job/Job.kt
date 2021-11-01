package gghactions.model.job

import gghactions.model.Element
import gghactions.model.step.Step

class Job : Element {
    val steps: Sequence<Step> = emptySequence()

    override fun render(builder: StringBuilder, indent: String) {
        TODO("Not yet implemented")
    }
}
