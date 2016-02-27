package fr.xebia.xke.fp5.serialization.serializer.string

import fr.xebia.xke.fp5.serialization.model.Person
import fr.xebia.xke.fp5.serialization.serializer.Serializer.SerializationError
import fr.xebia.xke.fp5.serialization.serializer.string.StandardTypeSerializationSupport._
import fr.xebia.xke.fp5.serialization.serializer.{Serializable, Serializer}

import scalaz.{Failure, Success, Validation}

object CustomTypesSerializationSupport {

  implicit object PersonSerializationSupport extends Serializable[Person] {

    val PersonPattern = """Person\((.*),(.*),(.*)\)""".r

    override def deserialize(s: String): Validation[SerializationError, Person] = s match {
      case PersonPattern(eventualFirstName, eventualLastName, eventualMail) =>
        for {
          firstName <- Serializer.read[String](eventualFirstName)
          lastName <- Serializer.read[String](eventualLastName)
          mail <- Serializer.read[Option[String]](eventualMail)
        } yield Person(firstName, lastName, mail)

      case _ => Failure("Expected Person")
    }

    override def serialize(t: Person): Validation[SerializationError, String] =
      Success(t.toString)

  }

}