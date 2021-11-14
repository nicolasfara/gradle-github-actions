package gghactions.model.github

import com.charleskorn.kaml.PolymorphismStyle
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import kotlinx.serialization.Serializable

@Serializable
sealed class Step {
    abstract var name: String
    abstract var `if`: String
    abstract var env: Map<String, String>
}

@Serializable
data class Cli(
    override var name: String,
    override var `if`: String = "",
    override var env: Map<String, String> = emptyMap(),
    val shell: String = "",
    val run: String,
) : Step()

@Serializable
data class Gradle(
    override var name: String,
    override var `if`: String = "",
    override var env: Map<String, String> = emptyMap(),
    @Serializable(with = TaskToStringSerializer::class)
    val tasks: List<String>
) : Step()

@Serializable
data class Action(
    override var name: String,
    override var `if`: String = "",
    override var env: Map<String, String> = emptyMap(),
    val uses: String,
    val with: Map<String, String> = emptyMap(),
) : Step()

fun main() {
    val gradle = Gradle(name = "Hello", tasks = listOf("check"))
    val job = Job(name = "okk", steps = listOf(gradle))
    val config = YamlConfiguration(encodeDefaults = false, polymorphismStyle = PolymorphismStyle.Property)
    print(Yaml(configuration = config).encodeToString(Job.serializer(), job))
}

