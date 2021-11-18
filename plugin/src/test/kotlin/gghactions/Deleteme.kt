package gghactions

import com.soywiz.korte.TemplateProvider
import com.soywiz.korte.Templates
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.nio.file.Paths

class Deleteme : WordSpec({
    "testing kortor" should {
        "work with yaml files" {
            val template = Templates(ResourceTemplateProvider("ci"))
            val ci = template.render("precomputed.yml", mapOf("name" to "Continuous Integration"))
            ci shouldBe "name: Continuous Integration"
        }
        "iterate over lists" {
            val template = Templates(ResourceTemplateProvider("ci"))
            val ci = template.render(
                "empty.yml",
                mapOf(
                    "name" to "Continuous Integration",
                    "jobList" to listOf(Job("hello"), Job("World"))
                )
            )
            println(ci)
            ci shouldContain "World"
        }
    }
})

data class Job(val name: String)

class ResourceTemplateProvider(private val basePath: String) : TemplateProvider {
    override suspend fun get(template: String): String? {
        return this::class.java.classLoader.getResource(Paths.get(basePath, template).toString()).readText()
    }
}
