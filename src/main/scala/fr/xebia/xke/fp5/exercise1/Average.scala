package fr.xebia.xke.fp5.exercise1

/**
  * Exercise de motivation pour le typeclass pattern. L'idée est d'implementer une fonctionne qui calcule la moyenne d'une liste
  * d'entiers, en suite d'une liste de double, et en suite d'une liste de numéros complexes
  */
object OopAverage {
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

/**
  * Exemple de solution en Scala avec l'utilisation de la type class NumberLike
  */
object TypeClassAverage {
  def avgInt(numbers: Int*): Double = {
    numbers.reduce(_ + _) / numbers.length
  }

  def avgDouble(numbers: Double*): Double = {
    numbers.reduce(_ + _) / numbers.length
  }

  trait NumberLike[T] {
    def +(a: T, b: T): T

    def /(a: T, n: T): T
  }

  object NumberLike {
    implicit object IntNumberLike extends NumberLike[Int] {
      override def +(a: Int, b: Int): Int = a + b

      override def /(a: Int, n: Int): Int = a / n
    }

    implicit object DoubleNumberLike extends NumberLike[Double] {
      override def +(a: Double, b: Double): Double = a + b

      override def /(a: Double, n: Double): Double = a / n
    }
  }

  def avgNumber[T](numbers: T*)(implicit numberLike: NumberLike[T], converter: Int => T) = {
    val sum = numbers.reduce((a, b) => numberLike.+(a, b))

    numberLike./(sum, converter(numbers.size))
  }

  /**
    * Example d'utilisation de avgNumber pour une liste de Int
    */
  lazy val avgIntNumber = avgNumber(5, 4, 3, 2, 1)

  /**
    * Example d'utilisation de avgNumber pour une liste de Double
    */
  lazy val avgDoubleNumber = avgNumber(5.0, 4.0, 3.0, 2.0, 1.0)

  /**
    * Example d'utilisation de avgNumber pour une liste de Int et Double mélangés
    */
  lazy val avgIntDoubleNumber = avgNumber(5.0, 4.0, 3.0, 2, 1)
}
