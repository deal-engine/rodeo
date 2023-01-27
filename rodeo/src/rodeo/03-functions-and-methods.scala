package rodeo

import zio.test._

object FunctionsAndMethods extends Chapter {
  // Functions describe transformation of data and are the basic building block of
  // functional programming.
  //
  // In Scala we have two types of «functions»: Functions and Methods.
  //
  // Methods are part of a class or an object, just like in OOP languages like Java.
  // We can usually treat them like normal functions, but they are not full functions
  // in a Functional way. These are defined with the `def` keyword.
  //
  // Functions are a little bit different. Instead, they are defined with the `val`
  // keyword. And that's very important. As you can see, they differ by the fact that
  // Functions are pure values and can be used in that way; they are an object by itself.
  //
  // Let's explore them in detail.

  Exercise("Function declarations") {
    // Methods can be defined with the reserved keyword `def` followed by a name, sets
    // of inputs (zero, one, many), and a single output.
    // NOTE: In Scala 2, the maximum number of inputs is 22, you can work around this
    // limitation by wrapping inputs in a class, tuple, or having multiple sets of inputs

    // Types are always required for inputs, and types are sometimes optional for
    // outputs, however we encourage you to almost always define them.

    // Zero inputs: they only generate values.
    def helloWorld(): String = {
      "Hello World"
    }
    assertTrue(helloWorld() == "Hello World")

    // One input.
    def generateGreeting(name: String): String = {
      s"Hello, ${name}"
    }
    assertTrue(generateGreeting("Patricio") == "Hello, Patricio")

    // Multiple inputs.
    def sum(numA: Int, numB: Int): Int = {
      numA + numB
    }
    assertTrue(sum(40, 2) == 42)

    // Exercise: Define a function that takes a number and a string and returns the string
    // repeated N times.
    def repeat(string: String, number: Int): String = ???

    assertTrue(
      repeat("Beetlejuice", 3) == "BeetlejuiceBeetlejuiceBeetlejuice",
      repeat("Hello", 2) == "HelloHello"
    )
  }

  Exercise("Functions declarations") {
    // We also have Functions that are true featured values, so can they are defined with 
    // `val` and their type signatures contain fat arrows `=>` describing the transformation
    // between types. This arrow is both used in the type and value world. In the value world
    // they are the actual mapping between values: the left hand side represent the parameter
    // and the right hand side represent the resulting value.
    val helloWorld: () => String = 
      () => "Hello World"
    assertTrue(helloWorld() == "Hello World")

    val generateGreeting: String => String =
      name => s"Hello, ${name}"
    assertTrue(generateGreeting("Patricio") == "Hello, Patricio")

    val sum: ((Int, Int)) => Int = { (numTuple: (Int, Int)) =>
      numTuple._1 + numTuple._2
    }
    assertTrue(sum(40, 2) == 42)

    // TODO Define a function that takes three numbers and returns the smallest one.
    val smallestNumber: (Int, Int, Int) => Int = ???

    assertTrue(
      smallestNumber(4, 7, 1) == 1,
      smallestNumber(100, 500, 10000) == 100
    )
  }

  Exercise("Functions via the FunctionN trait") {
    // Another way of representing these type signatures is using the `FunctionN[...]` traits
    // the last type input is the output type, and all the preceding type inputs are the
    // function inputs.
    val helloWorld: Function0[String] =
      () => "Hello, World"
    assertTrue(helloWorld() == "Hello World")

    val generateGreeting: Function1[String, String] =
      name => s"Hello, ${name}"
    assertTrue(generateGreeting("Patricio") == "Hello, Patricio")

    // TODO Define a function via the Function2 trait that takes two strings and reverses
    // the strings and then it concatenates them.
    val reverser: Function2[String, String, String] = {
      case (stringA, stringB) =>
        ???
    }
    assertTrue(reverser("Hello", "World") == "olleHdlroW")
  }

  Exercise("Default arguments") {
    // Default arguments
    // In Scala is possible to define default values for certain inputs.
    // With default arguments we don't need to always pass all arguments when calling methods.

    // NOTE: Only `def` functions/methods can have default arguments.
    //       Value `val` functions cannot have default arguments.
    def greeting(name: String, greet: String = "Hello, ") =
      greet + name
    assertTrue(
      greeting("Johnny") == "Hello, Johnny",
      greeting("Johnny", "Dear ") == "Dear Johnny"
    )

    // Exercise: Modify the following function that multiplies two numbers, such that
    // one of them is set to 2 by default.
    def multiplier(numberA: Int, numberB: Int): Int = {
      numberA * numberB
    }

    assertZIO(typeCheck("multiplier(4)"))(Assertion.isRight)
  }

  Exercise("Nested functions/methods") {
    // You can define functions within functions or methods within methods, it can
    // help with code de-duplication and code organization.
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

    // Also as values.
    // (If you don't understand the following syntax all that well, don't worry, it's
    // going to be explained later).
    val calculator2 = (a: Double) => (b: Double) => (op: String) => {
      val sum = (times: Int) => (a + b) * times

      val substraction =
        a - b

      op match {
        case "+"  => sum(1)
        case "++" => sum(2)
        case "-"  => substraction
      }
    }

    assertTrue(
      calculator2(20)(10)("+") == 30,
      calculator2(20)(10)("++") == 40,
      calculator2(20)(10)("-") == 10
    )

    // Exercise: Define a new calculator that implements multiplication as: "*" and
    // exponentiation as: "^".
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
    // Functions are the foundation of functional programming, thus they are treated as
    // importantly as any other value.

    def greeting(
        name: String,
        greeting: String = "Hello, ",
        textTransformation: String => String = 
          string => string // Observe how we are passing a function as an argument.
    ): String =
      textTransformation(greeting + name)

    assertTrue(greeting("Johnny", "Hi, ") == "Hi, Johnny")
    assertTrue(greeting("Johnny", "Hi, ", text => text.reverse) == "ynnhoJ ,iH")

    // Exercise: Define a function that receives another function, runs it 2 times and
    // returns the result as a sum.
    def duplicator(function: () => Int): Int = {
      ???
    }

    assertTrue(
      duplicator(() => 3) == 6,
      duplicator(() => 300) == 600
    )

    // But we can also return functions from other functions:
    val funExample = (a: Int) => (b: Int) => a + b 

    assertTrue(funExample(3)(4) == 7)

    // Or as a `def` calculator:
    def calculatorOP(operator: String): (Int, Int) => Int =
      operator match {
        case "+" =>
          (numA: Int, numB: Int) => numA + numB
        case "-" =>
          (numA: Int, numB: Int) => numA - numB
      }

    val addition = calculatorOP("+") 
    val substraction = calculatorOP("-")
    assertTrue(
      addition(1, 4) == 5,
      substraction(4, 1) == 3
    )

    // Exercise: Define a function that accepts a string and a function, and
    // returns a function that when it is run, it will return a transformed
    // string.
    def runner(str: String, function: String => String): () => String = {
      ???
    }

    assertTrue {
      val wooper: () => String = runner("Bar", _ => "Foo")
      wooper() == "Foo"
    }
  }

  Exercise("Partial application of a function") {
    // Partial application of a function: pass fewer arguments than the function expects,
    // and leave the rest as pending in order to return a new function that needs the rest
    // of the arguments to be applied.

    val logger = (message: String, prefix: String) => s"[${prefix}] $message"
    
    val infoLogger: String => String =
      pendingMessage => logger(pendingMessage, "INFO")

    // The underscore can be used as a placeholder for partially applied functions to leave
    // the pending arguments as the arguments of the function to be returned.
    val warnLogger: String => String = logger(_, "WARN")

    // C.S. Lecture: In reality, the underscore is an η-redex over lambdas, that is,
    // for a `f(_)` we have an abstraction `x => f(x)`. As you can see, the first one is 
    // easier to work with. Both `f(_)` and `x => f(x)` are semantically equivalent.

    assertTrue(
      infoLogger("Yeehaw") == "[INFO] Yeehaw",
      warnLogger("Ouch!") == "[WARN] Ouch!"
    )

    // Exercise: Define a function that accepts two numbers and multiplies them together,
    // and then partially apply that function to create a new one that always multiples by 10
    val multiplication: (Int, Int) => Int = ???
    val multiplyByTen: Int => Int = ???
    assertTrue(
      multiplyByTen(10) == 100,
      multiplyByTen(13) == 130
    )
  }

  Exercise("Curried functions") {
    // Curried functions are the decomposition of multi-argument functions into a sequence of
    // single-argument functions. The name Curry derives from Curry Haskell, a mathematician,
    // Partial application is commonly paired with currying

    val multiplicator = (times: Int) => (value: Int) => times * value

    val duplicator = multiplicator(2)
    assertTrue(
      duplicator(100) == 200,
      duplicator(25) == 50
    )

    // NOTE: Curried functions are easier to use if you define them as `val`, rather than `def`
    // because you need to specify placeholders for the remaining arguments and can't partially apply them
    // unless you manually η-abstract over them using lambdas.
    def adder(baseNumber: Int)(value: Int): Int =
      baseNumber + value

    def addTwo: Int => Int = { number =>
      adder(2)(number)
    }
    assertTrue(addTwo(40) == 42)

    val addTwoWVal = (n: Int) => adder(2)(n)
    assertTrue(addTwoWVal(40) == 42)

    // Exercise: Define a curried function that initially accepts a number, and then another function
    // from Int => String and returns a Seq of the results of iterating from 0 up to the specified number.
    // This is a good example to show how curried functions help implement functions that receive a function
    // as the last parameter because the last set of arguments can be seen as a block of code.
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

  Exercise("Pattern Matching") {
    // Pattern matching is a way to match the structure of a value, it can be used to
    // provide a «switch» or «case» like behavior, however it's more powerful.
    //
    // You can match against all kinds of values, such as:
    // - Simple values like: `true`, `false`, `null`, or strings
    // - Compound values like: Tuples, case classes, collections, and more
    //
    // It's also possible to match against more complex values like:
    // - Regular expressions
    // - Type patterns
    // - Guards (matching with conditions)

    // Simple values
    val result = "Hello" match {
      case "Hello" => "World"
      case _ => "Nothing"
    }
    assertTrue(result == "World")

    // Compound values
    val result2 = (1, 2, 3, 4) match {
      case (_, 2, _, 4) => "Matched!"
      case _ => "Not matched"
    }
    assertTrue(result2 == "Matched!")

    // Regular expressions
    val result3 = "Hello World" match {
      case s"Hello $name" => s"Hi, $name"
      case _ => "Not matched"
    }
    assertTrue(result3 == "Hi, World")

    // Type patterns
    val result4 = List(1, 2, 3, 4) match {
      case list: List[Int] => "List of ints!"
      case _ => "Not matched"
    }
    assertTrue(result4 == "List of ints!")

    // Guards
    val result5 = "Hello World" match {
      case str if str.length > 10 => "Long String"
      case _ => "Not matched"
    }
    assertTrue(result5 == "Long String")

    // Exercise: Define a pattern matching that checks if a value is an Int and if it's
    // greater than 10, it should return it's square.
    val result6 = 10 match {
      case i: Int if i > 10 => ???
      case _ => "Not matched"
    }

    assertTrue(
      result6 == "It's a square",
      (10 match {
        case i: Int if i > 10 => "Not matched"
        case _ => "Not matched"
      }) == "Not matched"
    )

    // We can also pattern match in assignations. With tuples, we can
    // assign multiple values to multiple variables simultaneously.
    val (name, age, sex) = ("John", 30, "Male")
    assertTrue(
      name == "John",
      age == 30,
      sex == "Male"
    )

    // Exercise: Define three variables that assign the values of a Tuple3.
    val (a, b, c) = (Int.???, Int.???, Int.???)
    assertTrue(
      a == 10,
      b == 20,
      c == 30
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
}
