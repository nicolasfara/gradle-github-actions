package gghactions.model.github

import kotlinx.serialization.Serializable

@Serializable
sealed class Step {
    abstract var name: String
    abstract var `if`: String?
    abstract var env: Map<String, String>?
}

@Serializable
data class Cli(
    override var name: String,
    override var `if`: String? = null,
    override var env: Map<String, String>? = null,
    val shell: String = "",
    val run: String,
) : Step()

@Serializable
data class Gradle(
    override var name: String,
    override var `if`: String? = null,
    override var env: Map<String, String>? = null,
    @Serializable(with = TaskToStringSerializer::class)
    val tasks: List<String>
) : Step()

@Serializable
data class Action(
    override var name: String,
    override var `if`: String? = null,
    override var env: Map<String, String>? = null,
    val uses: String,
    val with: Map<String, String> = emptyMap(),
) : Step()
