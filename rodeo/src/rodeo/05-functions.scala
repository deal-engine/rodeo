package rodeo

import zio.test._

import scala.util.{Failure, Success, Try}

object Functions extends Chapter {
  // Functions describe transformation of data and are the basic building block of functional programming.

  Exercise("Function declarations") {
    // Functions can be defined with the reserved keyword `def`
    // followed by a name, sets of inputs (zero, one, many), and a single output
    // NOTE: In Scala 2, the maximum number of inputs is 22, you can work around this limitation
    // by wrapping inputs in a class, tuple, or having multiple sets of inputs

    // Types are always required for inputs, and types are sometimes optional for outputs, however we encourage
    // you to almost always define them

    // Zero inputs: they only generate values
    def helloWorld(): String = {
      "Hello World"
    }
    assertTrue(helloWorld() == "Hello World")

    // One input
    def generateGreeting(name: String): String = {
      s"Hello, ${name}"
    }
    assertTrue(generateGreeting("Patricio") == "Hello, Patricio")

    // Multiple inputs
    def sum(numA: Int, numB: Int): Int = {
      numA + numB
    }
    assertTrue(sum(40, 2) == 42)

    // TODO Define a function that takes a number and a string and returns the string repeated N times
    def repeat(string: String, number: Int): String = ???

    assertTrue(
      repeat("Beetlejuice", 3) == "BeetlejuiceBeetlejuiceBeetlejuice",
      repeat("Hello", 2) == "HelloHello"
    )
  }

  Exercise("Functions as values") {
    // Functions can also be represented as values, so can be defined with `val`
    // and their type signatures contain fat arrows `=>` describing the transformation
    // between types
    val helloWorld: () => String = { () =>
      "Hello World"
    }
    assertTrue(helloWorld() == "Hello World")

    val generateGreeting: String => Unit = { name =>
      s"Hello, ${name}"
    }
    assertTrue(generateGreeting("Patricio") == "Hello, Patricio")

    val sum: ((Int, Int)) => Int = { (numTuple: (Int, Int)) =>
      numTuple._1 + numTuple._2
    }
    assertTrue(sum(40, 2) == 42)

    // TODO Define a function as value that takes three numbers and returns the smallest one
    val smallestNumber: (Int, Int, Int) => Int = ???

    assertTrue(
      smallestNumber(4, 7, 1) == 1,
      smallestNumber(100, 500, 10000) == 100
    )
  }

  Exercise("Functions via the FunctionN trait") {
    // Another way of representing these type signatures is using the `FunctionN[...]` traits
    // the last type input is the output type, and all the preceding type inputs are the function inputs
    val helloWorld: Function0[String] = { () =>
      "Hello, World"
    }
    assertTrue(helloWorld() == "Hello World")

    val generateGreeting: Function1[String, String] = { name =>
      s"Hello, ${name}"
    }
    assertTrue(generateGreeting("Patricio") == "Hello, Patricio")

    // Note that an advantage of using value functions is that you can easily leverage pattern matching
    // using the `case` keyword to destructure inputs
    val sum: Function2[Int, Int, Int] = { case (numA, numB) =>
      numA + numB
    }
    assertTrue(sum(40, 2) == 42)

    // TODO Define a function via the Function2 trait that takes two strings and reverses the strings and then it concatenates them
    val reverser: Function2[String, String, String] = {
      case (stringA, stringB) =>
        ???
    }
    assertTrue(reverser("Hello", "World") == "olleHdlroW")
  }

  Exercise("Default arguments") {
    // Default arguments
    // It is possible to define default values for certain inputs, in order
    // that way we dont need to always pass all arguments when calling functions

    // NOTE: Only `def` functions can have default arguments
    //       Value functions cannot have default arguments
    def greeting(name: String, greet: String = "Hello, ") =
      greet + name
    assertTrue(
      greeting("Johnny") == "Hello, Johnny",
      greeting("Johnny", "Dear ") == "Dear Johnny"
    )

    // TODO Define a function that multiples two numbers, and one of them is set to 2 by default
    def multiplier(numberA: Int, numberB: Int): Int = {
      numberA * numberB
    }

    assertZIO(typeCheck("multiplier(4)"))(Assertion.isRight)
  }

  Exercise("Nested functions") {
    // Nested functions
    // You can define functions within functions, if it helps with code de-duplication, and code organzation
    def calculator(numberA: Double, numberB: Double, operator: String) = {
      def sum(times: Int) =
        (numberA + numberB) * times

      def substraction =
        numberA - numberB

      operator match {
        case "+"  => sum(1)
        case "++" => sum(2)
        case "-"  => substraction
      }
    }

    assertTrue(
      calculator(20, 10, "+") == 30,
      calculator(20, 10, "++") == 40,
      calculator(20, 10, "-") == 10
    )

    // TODO Define a new calculator that implements multiplication as: "*" and exponentiation as: "^"
    def powerfulCalculator(
        numberA: Double,
        numberB: Double,
        operator: String
    ): Double = {
      ???
    }

    assertTrue(
      powerfulCalculator(2, 4, "*") == 8,
      powerfulCalculator(2, 4, "^") == 16,
      powerfulCalculator(10, 4, "^") == 10000
    )
  }

  Exercise("High order functions") {
    // High order functions
    // Functions are the foundation of functional programming, thus they are treated as importantly as any other value

    def greeting(
        name: String,
        greeting: String = "Hello, ",
        textTransformation: String => String = string => string
    ): String =
      textTransformation(greeting + name)

    assertTrue(greeting("Johnny", "Hi, ") == "Hi, Johnny")
    assertTrue(greeting("Johnny", "Hi, ", text => text.reverse) == "ynnhoJ ,iH")

    // TODO Define a function that receives another function, runs it 2 times and returns the result in a Seq
    def duplicator(function: () => Int): Seq[Int] = {
      ???
    }
    assertTrue(
      duplicator(() => 3) == Seq(3, 3),
      duplicator(() => 300) == Seq(300, 300)
    )
  }

  Exercise("Functions can be returned as values from other functions") {
    // It is sometimes useful to have functions that generate other functions
    // Here is a function that generates mathematical operations
    def calculator(operator: String): (Int, Int) => Int =
      operator match {
        case "+" =>
          (numA: Int, numB: Int) => numA + numB
        case "-" =>
          (numA: Int, numB: Int) => numA - numB
      }

    val addition = calculator("+")
    val substraction = calculator("-")
    assertTrue(
      addition(1, 4) == 5,
      substraction(4, 1) == 3
    )

    // TODO Define a function that accepts a number N and a function, and returns a function that when it is run,
    //      it will run N times and return the results as a Seq
    def runner(number: Int, function: () => String): () => Seq[String] = {
      ???
    }

    assertTrue {
      val wooper: () => Seq[String] = runner(3, () => "Woop")
      wooper() == Seq("Woop", "Woop", "Woop")
    }
  }

  Exercise("Partial functions") {
    // Partial functions are functions that are not defined for all of their inputs.
    // If a function throws an exception, it means it is a partial function

    // For example, division is not defined when the denominator is 0, so we can reflect that with a PartialFunction
    val division = new PartialFunction[(Double, Double), Double] {
      override def isDefinedAt(x: (Double, Double)): Boolean = x match {
        case (_, 0) => false
        case _      => true
      }

      override def apply(v1: (Double, Double)): Double = v1._1 / v1._2

      // Partial functions also implement a method called `lift` which lets you attempt to apply the function, and if it
      // is undefined for some inputs, the result is None, and if it defined for certain inputs, the result is Some(_)
    }
    assertTrue(
      division.isDefinedAt(10, 0) == false,
      division.lift(10, 0).isEmpty,
      division.isDefinedAt(10, 100) == true,
      division.lift(10, 100).isDefined
    )

    // TODO Define a function that only is able to add to even numbers, but not any odd numbers
    val evenAdder: PartialFunction[(Int, Int), Int] = ???
    assertTrue(
      evenAdder.isDefinedAt(4, 100),
      evenAdder.lift(2, 2).isDefined,
      evenAdder.isDefinedAt(3, 100) == false,
      evenAdder.lift(2, 1).isEmpty
    )
  }

  Exercise("Partial application of a function") {
    // Partial application of a function: pass fewer arguments than the function expects, and leave the rest as pending
    // in order to return a new function that needs the rest of the arguments to be applied.
    // NOTE: Partial application is not the same as partial functions
    // This is similar to a closure

    val logger = (message: String, prefix: String) => s"[${prefix}] $message"
    val infoLogger: String => String =
      pendingMessage => logger(pendingMessage, "INFO")

    // The underscore can be used as a placeholder for partially applied functions to leave the pending arguments as
    // the arguments of the function to be returned
    val warnLogger: String => String = logger(_, "WARN")

    assertTrue(
      infoLogger("Yeehaw") == "[INFO] Yeehaw",
      warnLogger("Ouch!") == "[WARN] Ouch!"
    )

    // TODO Define a function that accepts two numbers and multiplies them together, and then partially apply that function
    //      to create a new one that always multiples by 10
    val multiplication: (Int, Int) => Int = ???
    val multiplyByTen: Int => Int = ???
    assertTrue(
      multiplyByTen(10) == 100,
      multiplyByTen(13) == 130
    )
  }

  Exercise("Curried functions") {
    // Curried functions are the decomposition of multi-argument functions into a sequence of single-argument functions
    // Partial application is commonly paired with currying

    val multiplicator = (times: Int) => (value: Int) => times * value

    val duplicator = multiplicator(2)
    assertTrue(
      duplicator(100) == 200,
      duplicator(25) == 50
    )

    // NOTE: Curried functions are easier to use if you define them as `val`, rather than `def`
    // because you need to specify placeholders for the remaining arguments and can't partially apply them
    def adder(baseNumber: Int)(value: Int): Int =
      baseNumber + value

    def addTwo: Int => Int = { number =>
      adder(2)(number)
    }
    assertTrue(addTwo(40) == 42)

    // TODO Define a curried function that initially accepts a number, and then another function from Int => String
    //      and returns a Seq of the results of iterating from 0 up to the specified number.
    //      This is a good example to show how curried functions help implement functions that receive a function as the
    //      last parameter because the last set of arguments can be seen as a block of code
    val runNTimes: Int => (Int => String) => Seq[String] = {
      ???
    }

    assertTrue {
      val result = runNTimes(3) { number =>
        s"Num is: ${number}"
      }
      result == Seq("Num is: 0", "Num is: 1", "Num is: 2")
    }
  }

  Exercise("Functions with variable arguments") {
    // put a * symbol next to a data type to declare as a VarArg
    def concatenate(values: String*) = {
      values.mkString("-")
    }
    assertTrue(concatenate("Hello", "Dear", "World") == "Hello-Dear-World")

    // TODO Define a function that receives a variable amount of numbers, and it adds them all up
    def adder(numbers: Int*): Int = {
      ???
    }
    assertTrue(
      adder(1, 2, 3, 4, 5) == 15,
      adder(1, 10, 100) == 111
    )
  }

  Exercise("Recursive functions") {
    // In scala recursive functions are the preferred way to implement loop logic.
    def factorial(number: Int): Int = {
      number match {
        case 1 => 1
        case n => n * factorial(n - 1)
      }
    }
    assertTrue(factorial(4) == 24)

    // TODO Implement the Fibonacci sequence using recursive functions
    // The Fibonacci sequence starts with 1, 1 and all the following elements are computed by adding the previous two
    // 1, 1, 2, 3, 5, 8, 13, ...
    def fibonacci(upTo: Int): Int = upTo match {
      case 0 => 1
      case 1 => 1
      case _ => ???
    }
    assertTrue(
      fibonacci(0) == 1,
      fibonacci(1) == 1,
      fibonacci(2) == 2,
      fibonacci(3) == 3,
      fibonacci(4) == 5,
      fibonacci(10) == 89
    )
  }

  Exercise("Call-by-value and Call-by-name arguments") {
    // Functions with call-by-value arguments are the common arguments we have seen before. These are eager arguments.
    // Functions with call-by-name arguments are arguments that have not been yet computed. These are lazy arguments.
    // NOTE Call-by-name arguments are re-computed every time you call them, they are akin to 0-argument functions

    // For example, this math sandbox function lets you pass in an expression that might throw an error, but instead of
    // blowing up, it will safely contain it and turn it into an option
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

  Exercise("Generic functions") {
    // Generic functions enable an additional customization layer for the parameters by specifying what features
    //  should have instead of defining an explicit type
    def whatIsThisValue[A](value: A): String = {
      s"Your value is ${value}"
    }
    assertTrue(whatIsThisValue(20) == "Your value is 20")
    assertTrue(whatIsThisValue(()) == "Your value is ()")
    // TODO Implement a .getClass function wrapper, that accepts a generic-type argument and converts it to String
    //  hint: change the Any type and declare a generic type instead
    def classer(a: Any): String = ???

    assertTrue(classer(2) == "class java.lang.Integer")
    assertTrue(classer(true) == "class java.lang.Boolean")
    assertTrue(classer("My string") == "class java.lang.String")
  }

  Exercise("Implicit arguments") {
    // Implicit arguments are automatically passed to functions that ask for them
    //  scala will try to look for implicit values that match the arg type
    def greeting(name: String)(implicit prefix: String): String = {
      s"${prefix} ${name}"
    }
    implicit val prefix: String = "Mr."
    assertTrue(greeting("Smith") == "Mr. Smith")

    // TODO modify implicitAdder to accept an implicit Int argument and declare said value below
    def implicitAdder(x: Int) = x+1

    // delete the 10 arg to let the implicit value take effect
    assertTrue(implicitAdder(10) == 21)
  }
}
