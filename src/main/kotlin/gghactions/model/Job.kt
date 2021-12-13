package gghactions.model

/**
 * TODO.
 * [build] TODO.
 * [publish] TODO.
 */
data class Job(
    val build: JobId,
    val publish: JobId? = null
)
