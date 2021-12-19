package gghactions.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * TODO.
 */
interface Step {
    /**
     * TODO.
     */
    var name: String

    /**
     * TODO.
     */
    var cond: String?

    /**
     * TODO.
     */
    var env: Map<String, String>?
}

/**
 * TODO.
 * [shell] TODO.
 * [run] TODO.
 */
data class Cli(
    override var name: String,
    @JsonProperty("if")
    override var cond: String? = null,
    override var env: Map<String, String>? = null,
    var shell: String? = null,
    var run: String
) : Step

/**
 * TODO.
 * [uses] TODO.
 * [with] TODO.
 */
data class Action(
    override var name: String,
    @JsonProperty("if")
    override var cond: String? = null,
    override var env: Map<String, String>? = null,
    var uses: String,
    var with: Map<String, String>? = null
) : Step
