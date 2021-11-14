package gghactions.model.github

import kotlinx.serialization.Serializable

@Serializable
data class Job(var name: String, var `runs-on`: List<String> = listOf("ubuntu-latest"), var steps: List<Step> = emptyList())
