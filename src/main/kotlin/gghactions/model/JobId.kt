package gghactions.model

import com.fasterxml.jackson.annotation.JsonProperty
import gghactions.configuration.DefaultValues

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
    var runsOn: String = "\${{ matrix.os }}",
    var env: Map<String, String>? = null,
    @JsonProperty("if")
    var cond: String? = null, // ktlint-disable
    var strategy: Matrix = Matrix(
        mutableMapOf(
            "os" to listOf(DefaultValues.defaultOs),
            "java-version" to listOf(DefaultValues.defaultJavaVersion)
        )
    ),
    var steps: List<Step>
)
