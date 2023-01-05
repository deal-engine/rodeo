package rodeo

import zio.test._

import scala.util.{Failure, Success, Try}

object FunctionsLeftovers extends Chapter {

  Exercise("Partial functions") {
    // Partial functions are functions that are not defined for all of their inputs.
    // If a function throws an exception, it means it is a partial function.

    // For example, division is not defined when the denominator is 0, so we can reflect that
    // with a PartialFunction.
    val division = new PartialFunction[(Double, Double), Double] {
      override def isDefinedAt(x: (Double, Double)): Boolean = x match {
        case (_, 0) => false
        case _      => true
      }

      override def apply(v1: (Double, Double)): Double = v1._1 / v1._2

      // Partial functions also implement a method called `lift` which lets you attempt to
      // apply the function, and if it is undefined for some inputs, the result is None, and
      // if it defined for certain inputs, the result is Some(_).
    }
    assertTrue(
      division.isDefinedAt(10, 0) == false,
      division.lift(10, 0).isEmpty,
      division.isDefinedAt(10, 100) == true,
      division.lift(10, 100).isDefined
    )

    // Exercise: Define a function that only is able to add to even numbers, but not any odd
    // numbers.
    val evenAdder: PartialFunction[(Int, Int), Int] = ???
    assertTrue(
      evenAdder.isDefinedAt(4, 100),
      evenAdder.lift(2, 2).isDefined,
      evenAdder.isDefinedAt(3, 100) == false,
      evenAdder.lift(2, 1).isEmpty
    )
  }

  Exercise("Call-by-value and Call-by-name arguments") {
    // Functions with call-by-value arguments are the common arguments we have seen before. These
    // are eager arguments.
    // Functions with call-by-name arguments are arguments that have not been yet computed. These
    // are lazy arguments.
    // NOTE: Call-by-name arguments are re-computed every time you call them, they are akin to
    // 0-argument functions.

    // For example, this math sandbox function lets you pass in an expression that might throw an
    // error, but instead of blowing up, it will safely contain it and turn it into an option.
    def mathSandbox(expression: => Int): Option[Int] =
      Try(expression).toOption

    assertTrue(
      mathSandbox(1 + 1) == Some(2),
      mathSandbox(1 / 0) == None
    )

    // if this function gets evaluated, this exercise will never pass
    def bomb(): Unit = throw new Exception("Boom! the bad function was evaluated/executed")

    // TODO Enable this function to take the bomb as a call-by-name argument
    def extremeFunction(goodEnding: Boolean, badEnding: Unit): Boolean = ???
    // will the bomb() function will be evaluated?
    assertTrue(extremeFunction(true, bomb() ) )
  }

  Exercise("Functions with variable arguments") {
    // put a * symbol next to a data type to declare as a VarArg
    def concatenate(values: String*) = {
      values.mkString("-")
    }
    assertTrue(concatenate("Hello", "Dear", "World") == "Hello-Dear-World")

    // Exercise: Define a function that receives a variable amount of numbers, and it adds
    // them all up
    def adder(numbers: Int*): Int = {
      ???
    }
    assertTrue(
      adder(1, 2, 3, 4, 5) == 15,
      adder(1, 10, 100) == 111
    )
  }
}
