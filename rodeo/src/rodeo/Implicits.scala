package rodeo

import zio.test._

object Implicits extends Chapter {

  // In Scala implicits can have several different meanings.
  // An implicit parameter is a parameter than can be passed to a function without specifying it explicitly.

  Exercise("Implicit parameters") {

    implicit val prefix: String = "Mrs."
    def greeting(name: String)(implicit prefix: String): String = {
      s"${prefix} ${name}"
    }

    assertTrue(greeting("Alice") == ???)

  }

  // In the above exercises the compiler was able to handle the implicit input because there was only one `implicit val` defined.

  Exercise("Implicit parameters2") {

    // The compiler is not happy with code below.
    // This is because we have two implicts values defined and the compiler can't decide which of this values to use.
    // Remove the necessary code in order to satisfy the "assertTrue" condition and make the compiler happy.implicit val two: Double = 2

    // implicit val two: Double = 2
    implicit val three: Double = 3.0
    def square()(implicit num: Double) = num * num
    assertTrue(square() == 4.0)
  }

  Exercise("Implicit parameters3") {
    // In certain cases we might want to implement a function whose exact output is known to us but we are unable to calculate it exactly.
    // For example, in order to approximate sqrt(2) we need to use to use newtons method. This method allows us to calculate sqrt(2) to arbitrary precision, but we never get the exact answer.
    // In cases like this implicit parameters can be useful. Using an implicit parameter we can define the required level of precision and forget about this input when calling the sqrt function.

    implicit val precision: Double = 0.00001
    def sqrt(x: Double)(implicit precision: Double) = {
      def sqrtIter(guess: Double): Double =
        if (isGoodEnough(guess)) guess
        else sqrtIter(improve(guess))

      def improve(guess: Double) =
        (guess + x / guess) / 2

      def isGoodEnough(guess: Double) =
        (guess * guess - x).abs < ???

      sqrtIter(1.0)
    }

    assertTrue(
      sqrt(2)(1) == ???
    ) // Try to answer this by analyzing the code above.
    assertTrue(sqrt(2) - sqrt(2)(1) < ???)

  }

  // Implicts can also be used in cases where you want to evaluate a function in an argument that is not of the required type.
  // Let's say that we have a recursive function that allows to compute the product of all the elements in a given list and we want to apply this function to a set.
  // We can achieve this by defining an implicit function  that takes a set and returns a list.

  Exercise("implicit conversions") {
    val luckyNuberSet = Set(7, 11, 13)
    implicit def setToInt(set: Set[Int]): List[Int] = set.toList
    val producuct = luckyNuberSet.fold(1)((x, y) => ???)
    assertTrue(producuct == ???)
  }

  Exercise("implicit conversions 2") {
    case class MexicanPesos(value: Double)
    case class BritishPounds(value: Double)
    implicit def PoundsToPesos(pounds: BritishPounds): MexicanPesos =
      MexicanPesos(pounds.value * 28)
    case class BookShops(name: String, poorFriendly: Boolean)
    val bookShopOfIndia = BookShops("bookshopofindia", true)
    val amazonBooks = BookShops("Amazon", false)
    def bookShopRecomendation(cashForBooks: MexicanPesos): BookShops =
      if (cashForBooks.value > 10000) amazonBooks else bookShopOfIndia
    val bookBudget = BritishPounds(5000)

    assertTrue(bookShopRecomendation(bookBudget) == ???)

  }

  // An implicit class can be used in cases where we want to add methods to a class that has already been defined.
  // This could be useful in the following scenarios:
  // 1) You are using a using a library that is not yours and you want to add a method for a class in this library.
  // 2) The method you want to implement does not make sense "globally".

  Exercise("implicit classes") {
    implicit class NumericList(values: List[Double]) {
      def mean: Double = values.sum / values.size
    }

    // When an implicit class is in our scope we can call the methods defined inside this class even this methods are not defined in the type that this class wraps.

    val numbers = List(77.7, 13.31, 11.11)

    assertTrue(numbers.mean == ???)
  }

  Exercise("implicit classes 2") {
    // Implicit classes only take one explicit parameter(the value on which you are adding the extension methods) but can take additional implicit parameters.
    // TODO Move this to a separate package
    type ??? = Nothing

    implicit class NumericListOps[A](values: List[A])(implicit
        num: Numeric[???]
    ) {
      def mean: Double = num.toDouble(values.sum) / values.length
    }

    val favoriteNumbers = List(7, 11, 13)
    assertTrue(favoriteNumbers.mean == ???)

  }
}
