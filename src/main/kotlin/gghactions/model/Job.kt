package gghactions.model

/**
 * TODO.
 * [build] TODO.
 * [publish] TODO.
 */
data class Job(
    var build: JobId,
    var publish: JobId? = null
)
