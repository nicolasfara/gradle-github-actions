package gghactions.model

import com.fasterxml.jackson.annotation.JsonProperty

data class JobId(
    val needs: List<String>? = null,
    @JsonProperty("runs-on")
    val runsOn: List<String>,
    val env: Map<String, String>? = null,
    val `if`: String? = null,
    val strategy: Matrix? = null,
    val steps: List<Step>
)
