package fr.xebia.xke.fp5.serialization.serializer.string

import fr.xebia.xke.fp5.serialization.helper.TryHelper._
import fr.xebia.xke.fp5.serialization.serializer.Serializer.SerializationError
import fr.xebia.xke.fp5.serialization.serializer.{Serializable, Serializer}

import scala.util.Try
import scalaz.{Failure, Success, Validation}


object StandardTypeSerializationSupport {

  implicit object BooleanSerializationSupport extends Serializable[Boolean, String] {

    override def deserialize(u: String): Validation[SerializationError, Boolean] =
      Try(u.toBoolean).toValidation

    override def serialize(t: Boolean): Validation[SerializationError, String] =
      Success(t.toString)

  }

  implicit object StringSerializationSupport extends Serializable[String, String] {

    override def deserialize(u: String): Validation[SerializationError, String] =
      Success(u)

    override def serialize(t: String): Validation[SerializationError, String] =
      Success(t)

  }

  implicit object IntSerializationSupport extends Serializable[Int, String] {

    override def deserialize(u: String): Validation[SerializationError, Int] =
      Try(u.toInt).toValidation

    override def serialize(t: Int): Validation[SerializationError, String] =
      Success(t.toString)

  }

  implicit object UnitSerializationSupport extends Serializable[Unit, String] {

    override def deserialize(u: String): Validation[SerializationError, Unit] = u match {
      case "Unit" => Success(())
      case _ => Failure("Expected Unit")
    }

    override def serialize(t: Unit): Validation[SerializationError, String] =
      Success("Unit")

  }

  implicit def optionSerializationSupport[A](implicit ev: Serializable[A, String]) = new Serializable[Option[A], String] {

    val SomePattern = """Some\((.*)\)""".r

    override def deserialize(u: String): Validation[SerializationError, Option[A]] = u match {
      case "None" => Success(None)

      case _ if SomePattern.findFirstMatchIn(u).isDefined =>
        val matchingGroups = SomePattern.findFirstMatchIn(u).get.subgroups
        Serializer.read[A, String](matchingGroups.head).map(a => Some(a))

      case _ => Failure("Expected Option")
    }

    override def serialize(t: Option[A]): Validation[SerializationError, String] =
      Success(t.toString)

  }

}