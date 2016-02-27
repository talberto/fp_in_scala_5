package fr.xebia.xke.fp5.serialization.serializer.json

import fr.xebia.xke.fp5.serialization.serializer.Serializer.SerializationError
import fr.xebia.xke.fp5.serialization.serializer.{Serializable, Serializer, string}

import scalaz.{Failure, Success, Validation}


object StandardTypeSerializationSupport {

  implicit val BooleanSerializationSupport = string.StandardTypeSerializationSupport.BooleanSerializationSupport

  implicit val StringSerializationSupport = string.StandardTypeSerializationSupport.StringSerializationSupport

  implicit val IntSerializationSupport = string.StandardTypeSerializationSupport.IntSerializationSupport

  implicit object UnitSerializationSupport extends Serializable[Unit] {

    override def deserialize(s: String): Validation[SerializationError, Unit] = s match {
      case "undefined" => Success(())
      case _ => Failure("Expected Unit")
    }

    override def serialize(t: Unit): Validation[SerializationError, String] =
      Success("undefined")

  }

  implicit def optionSerializationSupport[A](implicit ev: Serializable[A]) = new Serializable[Option[A]] {

    val NonePattern = """\{"present":false\}""".r
    val SomePattern = """\{"present":true, "value":(.*)\}""".r

    override def deserialize(s: String): Validation[SerializationError, Option[A]] = s match {
      case NonePattern() => Success(None)

      case SomePattern(eventualValue) =>
        Serializer.read[A](eventualValue).map(value => Some(value))

      case _ => Failure("Expected Option")
    }

    override def serialize(t: Option[A]): Validation[SerializationError, String] = t match {
      case None => Success(s"""{"present":false}""")
      case Some(value) => Serializer.write(value).map(value => s"""{"present":true, "value":$value}""")
    }

  }

  implicit def eitherSerializationSupport[A, B](implicit evA: Serializable[A], evB: Serializable[B]) =
    new Serializable[Either[A, B]] {

      val LeftPattern = """\{"branch":"left", "value":(.*)\}""".r
      val RightPattern = """\{"branch":"right", "value":(.*)\}""".r

      override def deserialize(u: String): Validation[SerializationError, Either[A, B]] = u match {
        case RightPattern(right) => Serializer.read[B](right).map(right => Right(right))
        case LeftPattern(left) => Serializer.read[A](left).map(left => Left(left))
        case _ => Failure("Expected Either")
      }

      override def serialize(t: Either[A, B]): Validation[SerializationError, String] = t match {
        case Left(left) => Serializer.write[A](left).map(left => s"""{"branch":"left", "value":$left}""");
        case Right(right) => Serializer.write[B](right).map(right => s"""{"branch":"right", "value":$right}""");
      }

    }

}