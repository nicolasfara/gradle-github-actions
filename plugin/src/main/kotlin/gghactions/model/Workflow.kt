package gghactions.model

data class Workflow(
    val name: String,
    val on: Map<String, Any>,
    val jobs: Job
)
