package fr.xebia.xke.fp5.serializer

import scala.util.control.NonFatal


trait Serializable[T] {

  def read(s: String): T =
    try fromString(s)
    catch {
      case NonFatal(e) => throw new ParseException(e)
    }

  def write(t: T): String =
    toString(t)

  def fromString(s: String): T

  def toString(t: T): String

}
