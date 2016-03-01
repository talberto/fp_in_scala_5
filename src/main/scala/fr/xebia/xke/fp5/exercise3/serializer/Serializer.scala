package fr.xebia.xke.fp5.exercise3.serializer

import scalaz.Validation

object Serializer {

  type SerializationError = String

  def write[T](target: T)(implicit ev: Serializable[T]): Validation[SerializationError, String] =
    ev.serialize(target)

  def read[T](s: String)(implicit ev: Serializable[T]): Validation[SerializationError, T] =
    ev.deserialize(s)

}