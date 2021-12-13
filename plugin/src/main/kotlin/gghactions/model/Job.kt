package gghactions.model

data class Job(
    val build: JobId,
    val publish: JobId? = null
)
