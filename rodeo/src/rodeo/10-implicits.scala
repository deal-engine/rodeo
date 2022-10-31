package rodeo

import zio.ZEnvironment
import zio.test._

object Implicits extends Chapter {

  // 1. Implicit parameters/arguments/inputs
  // 2. Implicit conversions (transformations between types)
  // 3. Implicit classes (extension methods)

  // In Scala implicits can have several different meanings.
  // An implicit parameter is a parameter than can be passed to a function without specifying it explicitly.

  Exercise("Implicit parameters") {

    implicit val prefix: String = "Mrs."

    def greeting(name: String, lastName: String)(implicit
        prefix: String
    ): String = {
      s"${prefix} ${name} ${lastName}"
    }

    // TODO ???
    assertTrue(greeting("Alice", "Gutierrez") == "Mrs. Alice Gutierrez")
  }

  // In the above exercises the compiler was able to handle the implicit input because there was only one `implicit val` defined.

  Exercise("Implicit parameters ambigouous") {
    // TODO See how can we compile this
    // implicit val two: Double = 2.0
    implicit val three: Double = 3.0

    // This is because we have two implicits values defined and the compiler can't decide which of this values to use.
    // Remove the necessary code in order to satisfy the "assertTrue" condition and make the compiler happy.

    def add(numA: Double)(implicit numB: Double): Double = numA + numB

    // TODO ???
    assertTrue(add(1) == 4)
  }

  Exercise("Implicit implicits") {
    implicit val three: Double = 3.0

    def add(numA: Double): Double = {
      val numB: Double = implicitly[Double]
      numA + numB
    }

    // TODO ???
    assertTrue(add(1) == 4)
  }

  Exercise("Implicit parameters adjust") {
    // In certain cases we might want to implement a function whose exact output is known to us but we are unable to
    // calculate it exactly.
    // For example, in order to approximate sqrt(2) we need to use to use newtons method. This method allows us to
    // calculate sqrt(2) to arbitrary precision, but we never get the exact answer.
    // In cases like this implicit parameters can be useful. Using an implicit parameter we can define the required
    // level of precision and forget about this input when calling the sqrt function.

    implicit val precision: Double = 0.00001

    def sqrt(x: Double)(implicit precision: Double): Double = {
      def sqrtIter(guess: Double): Double =
        if (isGoodEnough(guess)) guess
        else sqrtIter(improve(guess))

      def improve(guess: Double) =
        (guess + x / guess) / 2

      def isGoodEnough(guess: Double) =
        (guess * guess - x).abs < precision

      sqrtIter(1.0)
    }

    // TODO Change exercise for something less math-y and more programm-y
    assertTrue(
      sqrt(2)(1) == 1.5
    ) &&
    assertTrue(sqrt(2) - sqrt(2)(1) < 1)

  }

  Exercise("Implicit parameters in a class") {
    // TODO We dont use this
    // TODO Come up with the right design pattern
    case class Integer(value: Int)
    case class Sum(numA: Int)(implicit numB: Integer) {
      val result = numA + numB.value
    }

    object calculations {
      implicit val sum: (Int, Int) => Int =
        (numA, numB) => numA + numB

      implicit val mult: (Int, Int) => Int =
        (numA, numB) => numA * numB
    }
    case class Calculator(numA: Int, numB: Int)(implicit
        calculation: (Int, Int) => Int
    ) {
      val result = calculation(numA, numB)
    }

    implicit val int = Integer(5)

    import calculations.mult

    assertTrue(Sum(1).result == 6) &&
    // TODO Exercise
    assertTrue(Calculator(5, 6).result == 30)
  }

  // Implicts can also be used in cases where you want to evaluate a function in an argument that is not of the required type.
  // Let's say that we have a recursive function that allows to compute the product of all the elements in a given list and we want to apply this function to a set.
  // We can achieve this by defining an implicit function  that takes a set and returns a list.

  Exercise("Implicit conversions") {
    def greeting(
        name: String,
        greetings: Seq[String]
    ): String = greetings.mkString("", s", ${name} -- ", s", ${name}")

    assertTrue(
      greeting("Max", Seq("Hello")) == "Hello, Max"
    )

    implicit def stringToSeq(string: String): Seq[String] = Seq(string)
    // NOTE How greetings is now passing a String, and not a Seq[String] and still works, because of the implicit conversion
    assertTrue(greeting("Max", "Hello") == "Hello, Max")
  }

  Exercise("implicit conversions 2") {
    case class MexicanPesos(value: Double)
    case class BritishPounds(value: Double)
    implicit def PoundsToPesos(pounds: BritishPounds): MexicanPesos =
      MexicanPesos(pounds.value * 28)

//    def mexicanStore(cash: MexicanPesos): String =

//
//    assertTrue(bookShopRecomendation(bookBudget) == ???)

    assertTrue(true)
  }

  // An implicit class can be used in cases where we want to add methods to a class that has already been defined.
  // This could be useful in the following scenarios:
  // 1) You are using a using a library that is not yours and you want to add a method for a class in this library.
  // 2) The method you want to implement does not make sense "globally".

  Exercise("Implicit classes") {
    // implement Example and Exercise and Topic
    // Topic { Example { } Exercise { } }
    // TODO Change this exercise
    implicit class ListStat(values: List[Int]) {
      def mean: Double = values.sum / values.size
    }

    // When an implicit class is in our scope we can call the methods defined inside this class even this methods are not defined in the type that this class wraps.
    val numbers = List(10, 20)
    assertTrue(numbers.mean == 15)
  }

  Exercise("implicit classes 2") {
    // Implicit classes only take one explicit parameter(the value on which you are adding the extension methods) but can take additional implicit parameters.

    implicit class NumericListOps[A: Numeric](values: List[A]) {
      val numeric: Numeric[A] = implicitly[Numeric[A]]
      def mean: Double = numeric.toDouble(values.sum) / values.length
    }

    val favoriteNumbers = List(10.0, 30.0)
    assertTrue(favoriteNumbers.mean == 20.0)

    // NOTE: Another way of declaring Numeric restriction
//    implicit class NumericListOpsTwo[A](values: List[A])(implicit numeric: Numeric[A]) {
//      def mean: Double = numeric.toDouble(values.sum) / values.length
//    }
//
  }
}
