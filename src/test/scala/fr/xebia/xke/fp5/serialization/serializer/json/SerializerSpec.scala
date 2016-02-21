package fr.xebia.xke.fp5.serialization.serializer.json

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.{TextNode, BooleanNode}
import fr.xebia.xke.fp5.serialization.helper.ValidationHelper._
import fr.xebia.xke.fp5.serialization.serializer.Serializer._
import fr.xebia.xke.fp5.serialization.serializer.json.StandardTypeSerializationSupport._
import org.scalatest.{Assertions, FlatSpec, Matchers}

class SerializerSpec extends FlatSpec with Matchers with Assertions {

  // TODO EXO3
  "Serializable" should "serialize Booleans" in {
    write(true).value should equal(BooleanNode.TRUE)
    write(false).value should equal(BooleanNode.FALSE)
  }

  // TODO EXO3
  it should "deserialize Booleans" in {
    read[Boolean, JsonNode](BooleanNode.TRUE).value should equal(true)
    read[Boolean, JsonNode](BooleanNode.FALSE).value should equal(false)
  }

  // TODO EXO3
  it should "fail for invalid Boolean read" in {
    read[Boolean, JsonNode](TextNode.valueOf("true")).isFailure should be(true)
  }

  // TODO EXO4
  it should "serialize Strings" in {
    write("Hello world !").value should equal(TextNode.valueOf("Hello world !"))
  }

  // TODO EXO4
  it should "deserialize String" in {
    read[String, JsonNode](TextNode.valueOf("unserialize me")).value should equal("unserialize me")
  }

}
