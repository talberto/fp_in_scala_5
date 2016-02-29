package fr.xebia.xke.fp5.exercise3.serializer

import fr.xebia.xke.fp5.exercise3.serializer.Serializer.SerializationError

import scalaz.Validation


trait Serializable[T] {

  def deserialize(s: String): Validation[SerializationError, T]

  def serialize(t: T): Validation[SerializationError, String]

}
