package fr.xebia.xke.fp5.serialization.helper

import scalaz.{Failure, Success, Validation}

object ValidationHelper {

  implicit class RichValidation[E, A](validation: Validation[E, A]) {

    def value = validation match {
      case Success(result) => result
      case Failure(message) => throw new NoSuchElementException("Validation failed: " + message)
    }

  }

}
