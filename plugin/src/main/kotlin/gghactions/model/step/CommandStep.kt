package gghactions.model.step

import javax.inject.Inject

open class GradleCommand : Step() {
    var tasks = arrayListOf<String>()
    override var command = "run: ./gradlew ${tasks.joinToString(" ")}"
}
open class Command @Inject constructor() : Step() {
    var run: String = ""
    override var command = "run: $run"
}
