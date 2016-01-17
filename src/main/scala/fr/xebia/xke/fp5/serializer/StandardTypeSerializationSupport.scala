package fr.xebia.xke.fp5.serializer

object StandardTypeSerializationSupport {

  implicit object BooleanSerializationSupport extends Serializable[Boolean] {

    override def fromString(s: String): Boolean = s.toBoolean

    override def toString(t: Boolean): String = java.lang.Boolean.toString(t)

  }

  implicit object StringSerializationSupport extends Serializable[String] {

    override def fromString(s: String): String = s

    override def toString(t: String): String = t

  }

  implicit object IntSerializationSupport extends Serializable[Int] {

    override def fromString(s: String): Int = s.toInt

    override def toString(t: Int): String = t.toString

  }

  implicit object UnitSerializationSupport extends Serializable[Unit] {

    override def fromString(s: String): Unit =
      if (s == "Unit") ()
      else throw new IllegalArgumentException("Expected Unit")

    override def toString(t: Unit): String = "Unit"

  }

  implicit def optionSerializationSupport[A](implicit ev: Serializable[A]) = new Serializable[Option[A]] {

    val SomePattern = """Some\((.*)\)""".r

    override def fromString(s: String): Option[A] = s match {
      case "None" => None
      case _ if SomePattern.findFirstMatchIn(s).isDefined =>
        val matchingGroups = SomePattern.findFirstMatchIn(s).get.subgroups
        Some(Serializer.read[A](matchingGroups.head))
      case _ => throw new IllegalArgumentException("Expected Option")
    }

    override def toString(t: Option[A]): String = t.toString

  }

}