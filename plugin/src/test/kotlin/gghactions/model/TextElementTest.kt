package gghactions.model

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

/**
 * Tests for [TextElement].
 */
class TextElementTest : WordSpec({
    val builder = StringBuilder()
    "A TextElement" should {
        "append a new line in his representation" {
            val element = TextElement("this is a element")
            element.render(builder)
            builder.toString() shouldBe "this is a element\n"
        }
    }
})
