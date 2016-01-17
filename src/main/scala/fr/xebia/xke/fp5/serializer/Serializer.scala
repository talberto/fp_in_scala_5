package fr.xebia.xke.fp5.serializer

object Serializer {

  def write[T](target: T)(implicit ev: Serializable[T]): String = {
    ev.write(target)
  }

  def read[T](target: String)(implicit ev: Serializable[T]): T = {
    ev.read(target)
  }

}
