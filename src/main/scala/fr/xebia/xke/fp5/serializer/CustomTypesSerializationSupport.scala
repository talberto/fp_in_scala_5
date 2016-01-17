package fr.xebia.xke.fp5.serializer

import fr.xebia.xke.fp5.serializer.StandardTypeSerializationSupport._

object CustomTypesSerializationSupport {

  implicit object PersonSerializationSupport extends Serializable[Person] {

    val PersonPattern = """Person\((.*),(.*),(.*)\)""".r

    override def fromString(s: String): Person = s match {
      case _ if PersonPattern.findFirstMatchIn(s).isDefined =>
        val matchingGroups = PersonPattern.findFirstMatchIn(s).get.subgroups
        val firstName = Serializer.read[String](matchingGroups(0).toString)
        val lastName = Serializer.read[String](matchingGroups(1).toString)
        val mail = Serializer.read[Option[String]](matchingGroups(2).toString)
        Person(firstName, lastName, mail)

      case _ => throw new IllegalArgumentException("Expected Person")
    }

    override def toString(t: Person): String = t.toString

  }

  implicit def eitherSerializationSupport[A, B](implicit evA: Serializable[A], evB: Serializable[B]) =
    new Serializable[Either[A, B]] {

      val RightPattern = """Right\((.*)\)""".r
      val LeftPattern = """Left\((.*)\)""".r

      override def fromString(s: String): Either[A, B] = s match {
        case RightPattern(right) => Right(Serializer.read[B](right))
        case LeftPattern(left) => Left(Serializer.read[A](left))
        case _ => throw new IllegalArgumentException("Expected Either")
      }

      override def toString(t: Either[A, B]): String = t.toString

    }

}