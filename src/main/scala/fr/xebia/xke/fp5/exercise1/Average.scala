package fr.xebia.xke.fp5.exercise1

/**
  * Exercise de motivation pour le typeclass pattern. L'idée est d'implementer une fonctionne qui calcule la moyenne d'une liste
  * d'entiers, en suite d'une liste de double, et en suite d'une liste de numéros complexes
  */
object OopAverage {

  /*
  Implémenter une fonction qui calcule l'average d'une liste d'entiers
   */
  def avgInt(numbers: Int*): Int = numbers.reduce(_ + _) / numbers.length

  /*
  Implémenter une fonction qui calcule l'average d'une liste d'entiers
   */
  def avgDouble(numbers: Double*): Double = numbers.reduce(_ + _) / numbers.length


  /**
    * Q: quelle est la différence entre les fonctions avgInt et avgDouble?
    *
    * Q: Peut-on faire une fonction générique qui traite les Int et les Double à la fois? Dit
    * autrement, peut-on mutualiser le code des deux fonctions?
    * R: Oui, on peut, en utilisant par example le pattern Wrapper/Adapter
    *
    */
  sealed trait NumberAdapter {
    def +(n: NumberAdapter): NumberAdapter

    def /(n: NumberAdapter): NumberAdapter
  }

  def avgNumber(numbers: NumberAdapter*): NumberAdapter = ??? /*
                                                              YOUR CODE HERE
                                                               */

  /**
    * Example d'utilisation de avgNumber pour une liste de Int
    */
  lazy val avgIntNumber = avgNumber(IntNumberAdapter(5), IntNumberAdapter(4), IntNumberAdapter(3), IntNumberAdapter(2), IntNumberAdapter(1))

  /**
    * Example d'utilisation de avgNumber pour une liste de Double
    */
  lazy val avgDoubleNumber = avgNumber(DoubleNumberAdapter(5.0), DoubleNumberAdapter(4.0), DoubleNumberAdapter(3.0), DoubleNumberAdapter(2.0), DoubleNumberAdapter(1.0))

  /*
  YOUR CODE HERE
   */
}
