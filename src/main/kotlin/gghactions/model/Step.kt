package gghactions.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * TODO.
 */
interface Step {
    /**
     * TODO.
     */
    val name: String

    /**
     * TODO.
     */
    val cond: String?

    /**
     * TODO.
     */
    val env: Map<String, String>?
}

/**
 * TODO.
 * [shell] TODO.
 * [run] TODO.
 */
data class Cli(
    override val name: String,
    @JsonProperty("if")
    override val cond: String? = null,
    override val env: Map<String, String>? = null,
    val shell: String? = null,
    val run: String
) : Step

/**
 * TODO.
 * [uses] TODO.
 * [with] TODO.
 */
data class Action(
    override val name: String,
    @JsonProperty("if")
    override val cond: String? = null,
    override val env: Map<String, String>? = null,
    val uses: String,
    val with: Map<String, String>? = null
) : Step
