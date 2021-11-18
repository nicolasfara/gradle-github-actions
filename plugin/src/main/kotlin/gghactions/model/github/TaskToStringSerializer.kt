package gghactions.model.github

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object TaskToStringSerializer : KSerializer<List<String>> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): List<String> {
        return decoder.decodeString().split(" ").filter { it != "./gradlew" }
    }

    override fun serialize(encoder: Encoder, value: List<String>) {
        encoder.encodeString("./gradlew ${value.joinToString(" ")}")
    }
}
