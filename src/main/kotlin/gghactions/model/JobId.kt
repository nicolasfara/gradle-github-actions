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
    var needs: List<String>? = null,
    @JsonProperty("runs-on")
    var runsOn: String,
    var env: Map<String, String>? = null,
    @JsonProperty("if")
    var cond: String? = null, // ktlint-disable
    var strategy: Matrix = Matrix(mutableMapOf("os" to listOf("ubuntu-latest"), "java-version" to listOf(17))),
    var steps: List<Step>
)
