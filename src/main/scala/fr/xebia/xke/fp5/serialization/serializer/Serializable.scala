package fr.xebia.xke.fp5.serialization.serializer

import fr.xebia.xke.fp5.serialization.serializer.Serializer.SerializationError

import scalaz.Validation


trait Serializable[T, U] {

  def deserialize(u: U): Validation[SerializationError, T]

  def serialize(t: T): Validation[SerializationError, U]

}
