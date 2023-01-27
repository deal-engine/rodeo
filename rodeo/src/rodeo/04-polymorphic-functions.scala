package rodeo

import zio.test._
import izumi.reflect.macrortti.LightTypeTagRef

object PolymorphicFunctions extends Chapter {
  // Until now we have been actively avoiding Polymorphism in functions to keep
  // this guide simple. But this is an essential part that we can't avoid.
  //
  // So far we've been using specific types, like Int, String, Double; all of
  // our functions were specific about the types. In Computer Sciences literature
  // we call this level of abstraction the "Simple Typed", in which terms can
  // only dependent on terms. So we have things like:
  //
  // ```scala
  // val intToInt: Int => Int = (a: Int) => a
  // val intVal: Int = 43
  // ```
  // 
  // What if we want to have an identity function that returns the same result
  // as the input (like `intToInt` but for everything)? In this level we can't
  // do that! But in Scala we can...
  //
  // A quick CS lecture:
  //
  //   In 1972 a french logician named Girard discovered the second-order
  //   lambda calculus. The lambda calculus is the most basic Functional
  //   Programming Language possible. Until now we have been using a superset
  //   of the Simple Typed Lambda Calculus, but what Girard adds to it is a
  //   way of generating universal quantification in types. In other words,
  //   with the addition of Girard, now we can have functions like:
  //     > for all possible types 'A', this function Identity is defined
  //     > from A to A. This is, in nerd notation, ∀α.α → α.
  //   So the language is no longer constrained to using specific types like
  //   Int, but we can generalize more our functions or types.
  //
  //   A little bit latter an extension called System fω was born. This
  //   allowed us to not only have polymorphic functions, but also have types
  //   that can be constructed by using other types. This is, types
  //   dependents on types, or special 'functions' that work over types.
  //
  // In this chapter we are going to explore some of this concepts in a
  // practical and useful way. So try to not feel too overwhelmed by this
  // introductions, it's actually easy.
  //
  // NOTE: This type of polymorphism is also called Parametric Polymorphism

  Exercise("Polymorphic functions") {
    // Let's suppose we want a function that returns the same element that it's given.
    // This is formally called the Identity Function. In Scala this can be defined as follows:
    def identity[A](a: A): A = a
    // That's quite a lot of "A"s.

    assertTrue(
      identity(4) == 4,
      identity("hi") == ("hi"),
      identity(true) == true
    )

    // Now that's really interesting. But we have a big problem without
    // easy solutions. Remember when we talked about value functions,
    // turns out they cannot be polymorphic in Scala 2! This is a big
    // limitation of the language. Thankfully this is solved in Scala 3,
    // which give us a syntax to properly make polymorphic functions:
    // ```scala3
    // val identity: [A] => A => A = [A] => (a: A) => a
    // ```
    // Yes, I know, that's a lot verbose, but it's what we have.

    // Let's see the `curry` function.
    // Given a uncurried function, we get a curried one with the same
    // semantics.
    def curry[A, B, C](f: (A, B) => C): A => B => C =
      (a: A) => (b: B) => f(a, b)

    assertTrue(
      curry { (a: Int, b: Int) => a + b } (2) (3) == 5
    )

    // We can also make a function that interchanges the order in a
    // curried function
    def flip[A, B, C](f: A => B => C): B => A => C =
      (b: B) => (a: A) => f(a)(b)

    assertTrue(
      flip(curry { (a: Int, b: Int) => a + b })(3)(2) == 5
    )

    // Exercise: Build a function that converts a curried function
    // into an uncurried one.
    def uncurry[A, B, C](f: A => B => C) = (a: A, b: B) => 0 // Change this

    assertTrue(
      uncurry(curry { (a: Int, b: Int) => a + b })(2, 3) == 5
    )
  }

  Exercise("Polymorphic types") {
    // We can also have polymorphic types. A common example is some type A
    // wrapped in an Option (which is a type constructor):
    val optInt: Option[Int] = Option(1)
    val optString: Option[String] = Option("hi")
    // Details about what exactly is Option doesn't matter right now, but
    // what it's interesting about it is the fact that it can have
    // different types of values inside.

    assertTrue(
      optInt == Option(1),
      optString == Option("hi")
    )

    // Other more common example are List or Arrays. In Java we can have
    // arrays that contain an specific type A:
    // ```java
    //  Integer[] intArray = {1, 2, 3};
    // ```
    // This is a great example of a polymorphic type. We can have an
    // array of any type A. The same applies to Lists in Scala:
    val intList: List[Int] = List(1, 2, 3)
    val stringList: List[String] = List("hi", "hello")

    assertTrue(
      intList == List(1, 2, 3),
      stringList == List("hi", "hello")
    )

    // We are going to explore later how this is useful and how we can
    // use it to make our programs more type safe and expressive.
  }

  Exercise("Tuples II") {
    // Remember the Tuples chapter? They are actually a polymorphic
    // type:
    // In Scala2, tuples can hold from one to 22 different values,
    // and their types are:  Tuple1[A], Tuple2[A, B], Tuple3[A, B, C], ...
    //
    // In Scala2, there is syntactic sugar to shorten those types:
    //   - Tuple1[A] = (A)
    //   - Tuple2[A, B] = (A, B)
    //   - Tuple3[A, B, C] = (A, B, C)
    //   - ...
    //
    // In Scala3 tuples are not restricted to 22 fields.
    //
    // In literature tuples are known as "products" `A x B` because they
    // can hold for every possible value of A, every possible value of B.
    // Here we are going to explore how we can exploit that polymorphism:

    // We can easily build a tuple in Scala:
    def tuple1[A](some: A): (Int, List[A], A) =
      (42, List(some, some), some)

    assertTrue(
      tuple1(42) == (42, List(42, 42), 42)
    )

    // We can also use it to simulate a primitive form of multiple
    // return values:
    def divide(a: Int, b: Int): (Int, Int) =
      (a / b, a % b)

    assertTrue(
      divide(42, 5) == (8, 2)
    )

    // Exercise: Build a function that given two functions, it builds
    // a new one that applies both functions and return the result as
    // a tuple.
    def combine[A, B, C](f1: A => B, f2: A => C): A => (B, C) =
      (a: A) => (???, ???) // Change this

    assertTrue(
      combine( (a: Int) => a + 1, (a: Int) => a * 2 ) (3) == (4, 6)
    )
  }
}
