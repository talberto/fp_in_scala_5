package fr.xebia.xke.fp5.serializer

import fr.xebia.xke.fp5.serializer.CustomTypesSerializationSupport._
import fr.xebia.xke.fp5.serializer.StandardTypeSerializationSupport._
import org.scalatest.{FlatSpec, Matchers}

class SerializerSpec extends FlatSpec with Matchers {

  "Serializable" should "serialize Booleans" in {
    Serializer.write(true) should equal("true")
    Serializer.write(false) should equal("false")
  }

  it should "deserialize Booleans" in {
    Serializer.read[Boolean]("true") should equal(true)
    Serializer.read[Boolean]("false") should equal(false)
  }

  it should "throw for invalid Boolean read" in {
    an[ParseException] should be thrownBy Serializer.read[Boolean]("maybe ?")
  }

  it should "serialize Strings" in {
    Serializer.write("Hello world !") should equal("Hello world !")
  }

  it should "deserialize String" in {
    Serializer.read[String]("unzerialize me") should equal("unzerialize me")
  }

  it should "serialize Ints" in {
    Serializer.write(50) should equal("50")
    Serializer.write(-15) should equal("-15")
  }

  it should "deserialize Ints" in {
    Serializer.read[Int]("0") should equal(0)
    Serializer.read[Int]("-113") should equal(-113)
  }

  it should "throw for invalid Int read" in {
    an[ParseException] should be thrownBy Serializer.read[Int]("50.5")
  }

  it should "serialize Unit" in {
    Serializer.write(()) should equal("Unit")
  }

  it should "deserialize Unit" in {
    Serializer.read[Unit]("Unit") should equal(())
  }

  it should "throw for invalid Unit read" in {
    an[ParseException] should be thrownBy Serializer.read[Unit]("void")
  }

  it should "serialize Option" in {
    Serializer.write(Option("str")) should equal("Some(str)")
    Serializer.write(None: Option[String]) should equal("None")
  }

  it should "deserialize Option" in {
    Serializer.read[Option[String]]("Some(string)") should equal(Option("string"))
    Serializer.read[Option[String]]("Some(true)") should equal(Option("true"))
    Serializer.read[Option[Boolean]]("Some(true)") should equal(Option(true))
    Serializer.read[Option[String]]("None") should equal(None)
  }

  it should "throw for invalid Option read" in {
    an[ParseException] should be thrownBy Serializer.read[Option[String]]("Success(ok)")
  }

  //!TODO EXO1
  it should "serialize Person" in {
    Serializer.write(Person("first", "last", None)) should equal("Person(first,last,None)")
    Serializer.write(Person("first", "last", Some("flast@gmail.com"))) should equal("Person(first,last,Some(flast@gmail.com))")
  }

  //!TODO EXO1
  it should "deserialize Person" in {
    Serializer.read[Person]("Person(martin,odersky,None)") should equal(Person("martin", "odersky", None))
  }

  //!TODO EXO1
  it should "throw for invalid Person read" in {
    an[ParseException] should be thrownBy Serializer.read[Person]("Person(hi)")
  }

  //!TODO EXO2
  it should "serialize Either" in {
    Serializer.write[Either[String, Int]](Left("error")) should equal("Left(error)")
    Serializer.write[Either[String, Int]](Right(12)) should equal("Right(12)")
  }

  //!TODO EXO2
  it should "deserialize Either" in {
    Serializer.read[Either[String, Int]] ("Left(error)") should equal(Left("error"))
    Serializer.read[Either[String, Int]] ("Right(42)") should equal(Right(42))
  }

  //!TODO EXO2
  it should "throw for invalid Either read" in {
    an[ParseException] should be thrownBy Serializer.read[Either[String, String]]("Wrong(15)")
  }

  it should "not compile without needed type class" in {
    "Serializer.write(List(1))" shouldNot typeCheck
  }

}
