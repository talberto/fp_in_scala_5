package fr.xebia.xke.fp5.serialization.serializer.string

import fr.xebia.xke.fp5.serialization.model.Person
import fr.xebia.xke.fp5.serialization.serializer.Serializer.SerializationError
import fr.xebia.xke.fp5.serialization.serializer.string.StandardTypeSerializationSupport._
import fr.xebia.xke.fp5.serialization.serializer.{Serializable, Serializer}

import scalaz.{Failure, Success, Validation}

object CustomTypesSerializationSupport {

  implicit object PersonSerializationSupport extends Serializable[Person, String] {

    val PersonPattern = """Person\((.*),(.*),(.*)\)""".r

    override def deserialize(u: String): Validation[SerializationError, Person] = u match {
      case _ if PersonPattern.findFirstMatchIn(u).isDefined =>
        val matchingGroups = PersonPattern.findFirstMatchIn(u).get.subgroups

        for {
          foo <- Serializer.read[String, String](matchingGroups(0).toString)
          bar <- Serializer.read[String, String](matchingGroups(1).toString)
          mail <- Serializer.read[Option[String], String](matchingGroups(2).toString)
        } yield Person(foo, bar, mail)

      case _ => Failure("Expected Person")
    }

    override def serialize(t: Person): Validation[SerializationError, String] =
      Success(t.toString)

  }


  implicit def eitherSerializationSupport[A, B](implicit evA: Serializable[A, String], evB: Serializable[B, String]) =
    new Serializable[Either[A, B], String] {

      val RightPattern = """Right\((.*)\)""".r
      val LeftPattern = """Left\((.*)\)""".r

      override def deserialize(u: String): Validation[SerializationError, Either[A, B]] = u match {
        case RightPattern(right) => Serializer.read[B, String](right).map(right => Right(right))
        case LeftPattern(left) => Serializer.read[A, String](left).map(left => Left(left))
        case _ => Failure("Expected Either")
      }

      override def serialize(t: Either[A, B]): Validation[SerializationError, String] =
        Success(t.toString)

    }

}