package gghactions.model.step

class ActionStep(actionName: String) : Step() {
    override var command = "uses: $actionName"
}
