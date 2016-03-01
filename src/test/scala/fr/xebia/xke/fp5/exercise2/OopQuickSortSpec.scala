package fr.xebia.xke.fp5.exercise2

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import OopQuickSort._

object OopQuickSortSpec extends Properties("OopQuickSort") {

  property("quickSortInt") = forAll { (numbers: Seq[Int]) =>
    quickSortInt(numbers: _*) == numbers.sorted
  }

}
