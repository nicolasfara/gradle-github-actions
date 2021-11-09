package gghactions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class GradleStepTest : WordSpec({
    lateinit var gradleStep: GradleStep

    beforeEach {
        gradleStep = GradleStep()
        gradleStep.name = "test step"
        gradleStep.tasks = listOf("test", "check")
    }

    "A GradleStep" `when` {
        "no condition is provided" should {
            "not print the condition parameter" {
                gradleStep.toString() shouldBe """
                    - name: test step
                      run: ./gradlew test check
                
                """.trimIndent()
            }
        }
        "a condition is provided" should {
            "print the condition parameter" {
                gradleStep.condition = "some condition"
                gradleStep.toString() shouldBe """
                    - name: test step
                      if: some condition
                      run: ./gradlew test check
                
                """.trimIndent()
            }
        }
        "env is set" should {
            "set the environment parameter" {
                gradleStep.env = mapOf(
                    "FOO" to "foo",
                    "BAR" to "bar"
                )
                gradleStep.toString() shouldBe """
                    - name: test step
                      run: ./gradlew test check
                      env:
                        FOO: foo
                        BAR: bar
                
                """.trimIndent()
            }
        }
        "toString is called without name" should {
            "throw an exception" {
                shouldThrow<UninitializedPropertyAccessException> {
                    GradleStep().toString()
                }
            }
        }
    }
})
