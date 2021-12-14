package gghactions.model

/**
 * TODO.
 * [name] TODO.
 * [on] TODO.
 * [jobs] TODO.
 */
data class Workflow(
    val name: String,
    val on: Map<String, Any>,
    val jobs: Job
)
