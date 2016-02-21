package fr.xebia.xke.fp5.serialization.helper

import fr.xebia.xke.fp5.serialization.serializer.Serializer
import Serializer.SerializationError

import scala.util.Try
import scalaz.{@@, Validation}


object TryHelper {

  implicit class RichTry[T](tryy: Try[T]) {
    def toEither: Either[SerializationError, T] =
      tryy match {
        case util.Success(r) => util.Right(r)
        case util.Failure(e) => util.Left(e.toString)
      }

    def toValidation: Validation[SerializationError, T] =
      Validation.fromEither(tryy.toEither)
  }

}
