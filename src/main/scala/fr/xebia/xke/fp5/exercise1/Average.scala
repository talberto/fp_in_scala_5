package fr.xebia.xke.fp5.exercise1

/**
  * Exercise de motivation pour le typeclass pattern. L'idée est d'implementer une fonctionne qui calcule la moyenne d'une liste
  * d'entiers, en suite d'une liste de double, et en suite d'une liste de numéros complexes
  */
object Average {
  def avgInt(numbers: Int*): Double = {
    numbers.reduce(_ + _) / numbers.length
  }

  def avgDouble(numbers: Double*): Double = {
    numbers.reduce(_ + _) / numbers.length
  }

  /**
    * Q: quelle est la différence entre les fonctions avgInt et avgDouble?
    *
    * Q: Peut-on faire une fonction générique qui traite les Int et les Double à la fois? Dit
    * autrement, peut-on mutualiser le code des deux fonctions?
    * R: Oui, on peut, en utilisant par example le pattern Wrapper/Adapter
    *
    */

  sealed trait Number {
    def +(n: Number): Number

    def /(n: Number): Number
  }

  case class IntNumberAdapter(i: Int) extends Number {
    override def +(n: Number): Number = {
      n match {
        case IntNumberAdapter(anotherInt) =>
          IntNumberAdapter(i + anotherInt)
        case _ =>
          throw new scala.IllegalArgumentException(s"The parameter passed $n isn't an instance of IntNumberAdapter")
      }
    }

    override def /(n: Number): Number = {
      n match {
        case IntNumberAdapter(anotherInt) =>
          IntNumberAdapter(i / anotherInt)
        case _ =>
          throw new scala.IllegalArgumentException(s"The parameter passed $n isn't an instance of IntNumberAdapter")
      }
    }
  }

  case class DoubleNumberAdapter(d: Double) extends Number {
    override def +(n: Number): Number = {
      n match {
        case DoubleNumberAdapter(anotherDouble) =>
          DoubleNumberAdapter(d + anotherDouble)
        case _ =>
          throw new scala.IllegalArgumentException(s"The parameter passed $n isn't an instance of DoubleNumberAdapter")
      }
    }

    override def /(n: Number): Number = {
      n match {
        case DoubleNumberAdapter(anotherDouble) =>
          DoubleNumberAdapter(d / anotherDouble)
        case _ =>
          throw new scala.IllegalArgumentException(s"The parameter passed $n isn't an instance of DoubleNumberAdapter")
      }
    }
  }

  object Number {
    def apply(i: Int) = IntNumberAdapter(i)

    def apply(d: Double) = DoubleNumberAdapter(d)

  }

  def avgNumber(numbers: Number*) = {
    numbers.reduce((a, b) => a + b) / Number(numbers.length)
  }

  /**
    * Example d'utilisation de avgNumber pour une liste de Int
    */

  lazy val avgIntNumber = avgNumber(Number(5), Number(4), Number(3), Number(2), Number(1))

  /**
    * Example d'utilisation de avgNumber pour une liste de Double
    */
  lazy val avgDoubleNumber = avgNumber(Number(5.0), Number(4.0), Number(3.0), Number(2.0), Number(1.0))

  /**
    * Example d'utilisation de avgNumber pour une liste de Int et Double mélangés
    */
  lazy val avgIntDoubleNumber = avgNumber(Number(5.0), Number(4.0), Number(3), Number(2), Number(1))
}
