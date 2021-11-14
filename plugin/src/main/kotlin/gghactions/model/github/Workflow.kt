package gghactions.model.github

import kotlinx.serialization.Serializable

@Serializable
data class Workflow(var name: String = "Continuous Integration", var on: List<String> = emptyList(), var jobs: List<Job> = emptyList())
