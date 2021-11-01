package gghactions.model.step

class GradleCommand : Step() {
    var tasks = arrayListOf<String>()
    override var command = "run: ./gradlew ${tasks.joinToString(" ")}"
}
class Command : Step() {
    var run: String = ""
    override var command = "run: $run"
}
