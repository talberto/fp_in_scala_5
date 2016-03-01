package fr.xebia.xke.fp5.exercise3.serializer.string

import fr.xebia.xke.fp5.exercise3.helper.TryHelper._
import fr.xebia.xke.fp5.exercise3.serializer.Serializer.SerializationError
import fr.xebia.xke.fp5.exercise3.serializer.{Serializable, Serializer}

import scala.util.Try
import scalaz.{Failure, Success, Validation}


object StandardTypeSerializationSupport {

  implicit object BooleanSerializationSupport extends Serializable[Boolean] {

    override def deserialize(s: String): Validation[SerializationError, Boolean] =
      Try(s.toBoolean).toValidation

    override def serialize(t: Boolean): Validation[SerializationError, String] =
      Success(t.toString)

  }

  implicit object StringSerializationSupport extends Serializable[String] {

    override def deserialize(s: String): Validation[SerializationError, String] =
      Success(s.stripPrefix("\"").stripSuffix("\""))

    override def serialize(t: String): Validation[SerializationError, String] =
      Success("\"" + t + "\"")

  }

  implicit object IntSerializationSupport extends Serializable[Int] {

    override def deserialize(s: String): Validation[SerializationError, Int] =
      Try(s.toInt).toValidation

    override def serialize(t: Int): Validation[SerializationError, String] =
      Success(t.toString)

  }

  implicit object UnitSerializationSupport extends Serializable[Unit] {

    override def deserialize(s: String): Validation[SerializationError, Unit] = s match {
      case "Unit" => Success(())
      case _ => Failure("Expected Unit")
    }

    override def serialize(t: Unit): Validation[SerializationError, String] =
      Success("Unit")

  }

  implicit def optionSerializationSupport[A](implicit ev: Serializable[A]) = new Serializable[Option[A]] {

    val SomePattern = """Some\((.*)\)""".r

    override def deserialize(s: String): Validation[SerializationError, Option[A]] = s match {
      case "None" => Success(None)
      case SomePattern(some) => Serializer.read[A](some).map(some => Some(some))
      case _ => Failure("Expected Option")
    }

    override def serialize(t: Option[A]): Validation[SerializationError, String] =
      Success(t.toString)

  }

  implicit def eitherSerializationSupport[A, B](implicit evA: Serializable[A], evB: Serializable[B]) =
    new Serializable[Either[A, B]] {

      val RightPattern = """Right\((.*)\)""".r
      val LeftPattern = """Left\((.*)\)""".r

      override def deserialize(u: String): Validation[SerializationError, Either[A, B]] = u match {
        case RightPattern(right) => Serializer.read[B](right).map(right => Right(right))
        case LeftPattern(left) => Serializer.read[A](left).map(left => Left(left))
        case _ => Failure("Expected Either")
      }

      override def serialize(t: Either[A, B]): Validation[SerializationError, String] = t match {
        case Left(left) => Serializer.write[A](left).map(left => s"Left($left)")
        case Right(right) => Serializer.write[B](right).map(right => s"Right($right)")
      }

    }

}