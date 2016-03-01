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
  def avgDouble(numbers: Double*): Double = ???


  /**
    * Q: quelle est la différence entre les fonctions avgInt et avgDouble?
    *
    * Q: Peut-on faire une fonction générique qui traite les Int et les Double à la fois? Dit
    * autrement, peut-on mutualiser le code des deux fonctions?
    *
    */
}
