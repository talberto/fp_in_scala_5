package fr.xebia.xke.fp5.exercise2


/**
  * Exercise de motivation pour le typeclass pattern. L'idée est d'implementer une fonctionne qui trie une liste d'entiers, ensuite
  * une liste de doubles et pour finir une liste de nombres rationelles
  */
object OopQuickSort {

  /**
    * Cette fonction trie une liste d'entiers par ordre ascendent
    */
  def quickSortInt(numbers: Int*): Seq[Int] = {
    numbers match {
      case Seq() =>
        Seq()
      case Seq(pivot, xs@_*) =>
        val smallerSorted = quickSortInt(xs.filter(_ <= pivot): _*)
        val biggerSorted = quickSortInt(xs.filter(_ > pivot): _*)
        smallerSorted ++ Seq(pivot) ++ biggerSorted
    }
  }

  def quickSortDouble(numbers: Double*): Seq[Double] = {
    numbers match {
      case Seq() =>
        Seq()
      case Seq(pivot, xs@_*) =>
        val smallerSorted = quickSortDouble(xs.filter(_ <= pivot): _*)
        val biggerSorted = quickSortDouble(xs.filter(_ > pivot): _*)
        smallerSorted ++ Seq(pivot) ++ biggerSorted
    }
  }

  /**
    * Exemples d'usage de deux fonctionnes de sort
    */
  lazy val sortedInt = quickSortInt(3, 2, 1)

  lazy val sortedDouble = quickSortDouble(3.0, 2.0, 1.0)

  /**
    * Q: Pourquoi peut-on implementer l'algorithme de tri pour les entiers?
    * R: Parce que il existe une fonction ou plusieurs fonctions qui nous permettent de dire si un entier et plus grand,
    * égal ou plus petit qu'un autre entier donné
    *
    * Q: Et pour les Double's?
    * R: Pour la même raison
    *
    * Q: Les fonctionnes qui nous permettent de comparér deux entiers ou deux réels, sont-ils les mêmes pour les deux cas?
    * R: Non, ils ne sont pas de fonctionnes paramétres, donc ils sont deux ensembles de fonctionnes différéntes.
    * Elles ne sont pas de fonctions paramétriques
    *
    * Q: Peut-on mutualiser le code des deux fonctionnes précédentes?
    * R: Oui, on peut utiliser un pattern Adapter avec la classe Ordered. C'est la même approche qu'en Java avec la classe
    * comparable
    */
  case class IntOrdered(i: Int) extends Ordered[IntOrdered] {
    override def compare(that: IntOrdered): Int = i - that.i
  }

  case class DoubleOrdered(d: Double) extends Ordered[DoubleOrdered] {
    override def compare(that: DoubleOrdered): Int = (d - that.d).toInt
  }

  def quickSortOrdered[T <: Ordered[T]](numbers: T*): Seq[T] = {
    numbers match {
      case Seq() =>
        Seq()
      case Seq(pivot, xs@_*) =>
        val smallerSorted = quickSortOrdered(xs.filter(_ <= pivot): _*)
        val biggerSorted = quickSortOrdered(xs.filter(_ > pivot): _*)
        smallerSorted ++ Seq(pivot) ++ biggerSorted
    }
  }

  /**
    * Example d'usage de quickSortOrdered
    */
  lazy val sortedIntOrdered = quickSortOrdered(IntOrdered(3), IntOrdered(2), IntOrdered(1))

  lazy val sortedDoubleOrdered = quickSortOrdered(DoubleOrdered(3.0), DoubleOrdered(2.0), DoubleOrdered(1.0))

  //lazy val sortedMixedOrdered = quickSortOrdered(IntOrdered(3), DoubleOrdered(2.0), DoubleOrdered(1.0))

  /**
    * Imaginons qu'on a téléchargé une librairie qui nous permet de manipuler des nombres rationnels. La classe ci-dessous implémént
    * les nombres rationnels apartient à cette librairie, et on ne peut pas donc la modifier.
    *
    * Q: Comment fait-on pour pouvoir trier une liste de nombres rationnels?
    * R: Avec l'approche précécent on doit récréer un nouveau adapter et wrapper chaque instance dans le wrapper pour le fournir
    * à la fonction de tri
    */
  case class RationalNumber(numerator: Int, denominator: Int)

  case class RationalNumberOrdered(rationalNumber: RationalNumber) extends Ordered[RationalNumberOrdered] {
    override def compare(that: RationalNumberOrdered): Int =
      (rationalNumber.numerator * that.rationalNumber.denominator) - (that.rationalNumber.numerator * rationalNumber.denominator)
  }

  /**
    * Exemple d'usage de quickSortOrdered avec un RationalNumber
    */
  lazy val sortedRationalOrdered = quickSortOrdered(
    RationalNumberOrdered(RationalNumber(3, 1)),
    RationalNumberOrdered(RationalNumber(2, 1)),
    RationalNumberOrdered(RationalNumber(1, 1))
  )
}


/**
  * Ordering typeclass way
  */
object TypeClassQuickSort {

  /**
    * "Primitive" way of using typeclasses. There must be an implicit in the scope of type Ordering[T]
    */
  def quickSortOrdering[T](numbers: T*)(implicit ev: Ordering[T]): Seq[T] = {
    numbers match {
      case Seq() =>
        Seq()
      case Seq(pivot, xs@_*) =>
        val smallerSorted = quickSortOrdering(xs.filter(x => ev.lteq(x, pivot)): _*)
        val biggerSorted = quickSortOrdering(xs.filter(x => ev.gt(x, pivot)): _*)
        smallerSorted ++ Seq(pivot) ++ biggerSorted
    }
  }

  /**
    * Idiomatic way of using typeclasses: [T: Ordering] means that T conforms to Ordering typeclass
    *
    * @param numbers
    * @tparam T
    * @return
    */
  def quickSortOrdering2[T: Ordering](numbers: T*): Seq[T] = {
    numbers match {
      case Seq() =>
        Seq()
      case Seq(pivot, xs@_*) =>
        val smallerSorted = quickSortOrdering2(xs.filter(x => implicitly[Ordering[T]].lteq(x, pivot)): _*)
        val biggerSorted = quickSortOrdering2(xs.filter(x => implicitly[Ordering[T]].gt(x, pivot)): _*)
        smallerSorted ++ Seq(pivot) ++ biggerSorted
    }
  }

  lazy val sortedIntOrdering = quickSortOrdering(3, 2, 1)

  lazy val sortedDoubleOrdering = quickSortOrdering(3.0, 2.0, 1.0)

  lazy val sortedMixedOrdering = quickSortOrdering(4, 3.0, 2, 1)

  /**
    * Imaginons qu'on a téléchargé une librairie qui nous permet de manipuler des nombres rationnels. La classe ci-dessous implémént
    * les nombres rationnels apartient à cette librairie, et on ne peut pas donc la modifier.
    *
    * Q: Comment fait-on pour pouvoir trier une liste de nombres rationnels?
    * R: Avec l'approche précécent on doit récréer un nouveau adapter et wrapper chaque instance dans le wrapper pour le fournir
    * à la fonction de tri
    */
  case class RationalNumber(numerator: Int, denominator: Int)

  implicit object RationalNumberOrdering extends Ordering[RationalNumber] {
    override def compare(x: RationalNumber, y: RationalNumber): Int =
      (x.numerator * y.denominator) - (y.numerator * x.denominator)
  }

  lazy val rationalNumberedOrdering = quickSortOrdering(RationalNumber(2, 1), RationalNumber(1, 1))

  //lazy val rationalMixedOrdering = quickSortOrdering(RationalNumber(2, 1), 1)
}
