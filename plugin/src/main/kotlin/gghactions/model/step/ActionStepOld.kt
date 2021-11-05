package gghactions.model.step

class ActionStepOld(actionName: String) : Step() {
    override var command = "uses: $actionName"
}
