package gghactions.model

/** A text-render element. */
interface Element {
    /**
     * Build the string representation of the element with a [builder].
     *
     * [indent] represent the indentation of the element.
     */
    fun render(builder: StringBuilder, indent: String = "")
}

/**
 * A text-renderable element.
 *
 * The [text] element is rendered with the given indent.
 */
class TextElement(private val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text\n")
    }
}
