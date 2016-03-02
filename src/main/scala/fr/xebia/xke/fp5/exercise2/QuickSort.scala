package fr.xebia.xke.fp5.exercise2

object OopQuickSort {

  /**
    * Cette fonction trie une liste d'entiers en ordre ascendent
    */
  def quickSortInt(numbers: Int*): Seq[Int] = numbers match {
    case Seq() =>
      Seq()
    case Seq(pivot, xs @ _*) =>
      val smallerSorted = quickSortInt(xs.filter(_ <= pivot): _*)
      val biggerSorted = quickSortInt(xs.filter(_ > pivot): _*)
      smallerSorted ++ Seq(pivot) ++ biggerSorted
  }

  lazy val sortedInt = quickSortInt(3, 2, 1) // res = Seq(1, 2, 3)

  /*
  OK, essaie maintenant de faire pareil pour une liste de double's
   */
  def quickSortDouble(numbers: Double*): Seq[Double] = numbers match {
    case Seq() =>
      Seq()
    case Seq(pivot, xs @ _*) =>
      val smallerSorted = quickSortDouble(xs.filter(_ <= pivot): _*)
      val biggerSorted = quickSortDouble(xs.filter(_ > pivot): _*)
      smallerSorted ++ Seq(pivot) ++ biggerSorted
  }

  lazy val sortedDouble = quickSortDouble(3.0, 2.0, 1.0) // res = Seq(1.0, 2.0, 3.0)

  /**
    * Q: Pourquoi peut-on implémenter l'algorithme de tri pour les entiers?
    *
    * Q: Et pour les Double's?
    *
    * Q: Les fonctionnes qui nous permettent de comparér deux entiers ou deux réels, sont-ils les mêmes pour les deux cas?
    *
    * Q: Peut-on mutualiser le code des deux fonctionnes précédentes?
    *
    */

  /**
    * Exemple de Wrapper/Adapter pour l'algorithme de tri
    */
  def quickSortOrdered[T <: Ordered[T]](numbers: T*): Seq[T] = ???

  /**
    * Example d'usage de quickSortOrdered
    */
  lazy val sortedIntOrdered = quickSortOrdered(IntOrdered(3), IntOrdered(2), IntOrdered(1))

  lazy val sortedDoubleOrdered = quickSortOrdered(DoubleOrdered(3.0), DoubleOrdered(2.0), DoubleOrdered(1.0))


}