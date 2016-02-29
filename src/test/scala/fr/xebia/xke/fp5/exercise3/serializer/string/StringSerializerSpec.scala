package fr.xebia.xke.fp5.exercise3.serializer.string

import fr.xebia.xke.fp5.exercise3.helper.ValidationHelper._
import fr.xebia.xke.fp5.exercise3.model.Person
import fr.xebia.xke.fp5.exercise3.serializer.Serializer
import fr.xebia.xke.fp5.exercise3.serializer.Serializer.{read, write}
import fr.xebia.xke.fp5.exercise3.serializer.string.CustomTypesSerializationSupport._
import fr.xebia.xke.fp5.exercise3.serializer.string.StandardTypeSerializationSupport._
import org.scalatest.{Assertions, FlatSpec, Matchers}

class StringSerializerSpec extends FlatSpec with Matchers with Assertions {

  "Serializable" should "serialize Booleans" in {
    write(true).value should equal("true")
    write(false).value should equal("false")
  }

  it should "deserialize Booleans" in {
    read[Boolean]("true").value should equal(true)
    read[Boolean]("false").value should equal(false)
  }

  it should "fail for invalid Boolean read" in {
    read[Boolean]("maybe ?").isFailure should be(true)
  }

  it should "serialize Strings" in {
    write("Hello world !").value should equal("\"Hello world !\"")
  }

  it should "deserialize String" in {
    read[String]("unserialize me").value should equal("unserialize me")
    read[String](""""unserialize me"""").value should equal("unserialize me")
  }

  it should "serialize Ints" in {
    write(50).value should equal("50")
    write(-15).value should equal("-15")
  }

  it should "deserialize Ints" in {
    read[Int]("0").value should equal(0)
    read[Int]("-113").value should equal(-113)
  }

  it should "fail for invalid Int read" in {
    read[Int]("50.5").isFailure should be(true)
  }

  it should "serialize Unit" in {
    write(()).value should equal("Unit")
  }

  it should "deserialize Unit" in {
    read[Unit]("Unit").value should equal(())
  }

  it should "fail for invalid Unit read" in {
    read[Unit]("void").isFailure should be(true)
  }

  it should "serialize Option" in {
    write(Option("str")).value should equal("Some(str)")
    write(None: Option[String]).value should equal("None")
  }

  it should "deserialize Option" in {
    read[Option[String]]("Some(string)").value should equal(Option("string"))
    read[Option[String]]("Some(true)").value should equal(Option("true"))
    read[Option[Boolean]]("Some(true)").value should equal(Option(true))
    read[Option[String]]("None").value should equal(None)
  }

  it should "fail for invalid Option read" in {
    read[Option[String]]("Success(ok)").isFailure should be(true)
  }

  it should "serialize Either" in {
    write[Either[String, Int]](Left("error")).value should equal("""Left("error")""")
    write[Either[String, Int]](Right(12)).value should equal("Right(12)")
  }

  it should "deserialize Either" in {
    read[Either[String, Int]]("Left(error)").value should equal(Left("error"))
    read[Either[String, Int]]("Right(42)").value should equal(Right(42))
  }

  it should "fail for invalid Either read" in {
    read[Either[String, String]]("Wrong(15)").isFailure should be(true)
  }

  //!TODO EXO1
  it should "serialize Person" in {
    write(Person("first", "last", None)).value should equal("Person(first,last,None)")
    write(Person("first", "last", Some("flast@gmail.com"))).value should equal("Person(first,last,Some(flast@gmail.com))")
  }

  //!TODO EXO1
  it should "deserialize Person" in {
    read[Person]("Person(martin,odersky,None)").value should equal(Person("martin", "odersky", None))
  }

  //!TODO EXO1
  it should "fail for invalid Person read" in {
    read[Person]("Person(hi)").isFailure should be(true)
  }

  it should "not compile without needed type class" in {
    "Serializer.write(List(1))" shouldNot typeCheck
  }

}
