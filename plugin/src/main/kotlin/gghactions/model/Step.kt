package gghactions.model

interface Step {
    val name: String
    val `if`: String?
    val env: Map<String, String>?
}

data class Cli(
    override val name: String,
    override val `if`: String? = null,
    override val env: Map<String, String>? = null,
    val shell: String? = null,
    val run: String
) : Step

data class Action(
    override val name: String,
    override val `if`: String? = null,
    override val env: Map<String, String>? = null,
    val uses: String,
    val with: Map<String, String>? = null
) : Step
