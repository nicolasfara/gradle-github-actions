package gghactions.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * TODO.
 * [needs] TODO.
 * [runsOn] TODO.
 * [env] TODO.
 * [cond] TODO.
 * [strategy] TODO.
 * [steps] TODO.
 */
data class JobId(
    val needs: List<String>? = null,
    @JsonProperty("runs-on")
    val runsOn: List<String>,
    val env: Map<String, String>? = null,
    @JsonProperty("if")
    val cond: String? = null, // ktlint-disable
    val strategy: Matrix? = null,
    val steps: List<Step>
)
