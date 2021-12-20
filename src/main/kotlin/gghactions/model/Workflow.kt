package gghactions.model

import gghactions.configuration.DefaultValues

/**
 * TODO.
 * [name] TODO.
 * [on] TODO.
 * [jobs] TODO.
 */
data class Workflow(
    var name: String,
    var on: Map<String, Any>,
    var jobs: Job
) {
    companion object {
        /**
         * Return the default workflow.
         */
        fun defaultConfig(): Workflow {
            return Workflow(
                name = "Build and Test",
                on = mapOf(
                    "push" to "",
                    "pull_request" to ""
                ),
                jobs = Job(build = JobId(steps = DefaultValues.defaultBuildStep))
            )
        }
    }
}
