package fr.xebia.xke.fp5.serialization.serializer

import scalaz.Validation

object Serializer {
  type SerializationError = String

  def write[T, U](target: T)(implicit ev: Serializable[T, U]): Validation[SerializationError, U] =
    ev.serialize(target)

  def read[T, U](target: U)(implicit ev: Serializable[T, U]): Validation[SerializationError, T] =
    ev.deserialize(target)

}