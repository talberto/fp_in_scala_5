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
  def quickSortDouble(numbers: Double*): Seq[Double] = ???

  lazy val sortedDouble = quickSortDouble(3.0, 2.0, 1.0) // res = Seq(1.0, 2.0, 3.0)

}