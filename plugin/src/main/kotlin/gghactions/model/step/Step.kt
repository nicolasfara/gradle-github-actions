package gghactions.model.step

import gghactions.model.Element

/**
 * A generic step in the CI.
 */
abstract class Step : Element {
    var name: String? = null
    protected abstract var command: String
    var env: Map<String, String> = emptyMap()

    override fun render(builder: StringBuilder, indent: String) {
        builder.appendLine("$indent- name: $name")
        builder.appendLine("$indent  $command")
        if (env.isNotEmpty()) {
            builder.appendLine(renderEnv(indent))
        }
        builder.appendLine()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }

    private fun renderEnv(indent: String): String {
        val builder = StringBuilder()
        builder.appendLine("$indent  env:")
        for ((k, v) in env) {
            builder.appendLine("$indent    $k: $v")
        }
        return builder.toString()
    }
}
