package fr.xebia.xke.fp5.serialization.serializer.json

import com.fasterxml.jackson.databind.JsonNode
import fr.xebia.xke.fp5.serialization.serializer.Serializable
import fr.xebia.xke.fp5.serialization.serializer.Serializer.SerializationError
import play.libs.Json.toJson

import scalaz.{Failure, Success, Validation}


object StandardTypeSerializationSupport {

  implicit object BooleanSerializationSupport extends Serializable[Boolean, JsonNode] {

    override def deserialize(u: JsonNode): Validation[SerializationError, Boolean] =
      if (u.isBoolean)
        Success(u.asBoolean())
      else
        Failure("Expected BooleanNode")

    override def serialize(t: Boolean): Validation[SerializationError, JsonNode] =
      Success(toJson(t))

  }

  implicit object StringSerializationSupport extends Serializable[String, JsonNode] {

    override def deserialize(u: JsonNode): Validation[SerializationError, String] =
      if (u.isTextual)
        Success(u.asText())
      else
        Failure("Expected TextNode")

    override def serialize(t: String): Validation[SerializationError, JsonNode] =
      Success(toJson(t))

  }

}