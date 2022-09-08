package rodeo

import scala.collection.mutable.{Seq => MutableSeq}

/**
 * Welcome to DealEngine scala rodeo.
 *
 * This material is intended for you to learn the basic of scala and some
 * common functional idioms.
 *
 */
object Variables {

  // Variables are references used to assign meaningful names to values.
  // Naming variables is one of the most important skills in programming.

  // A name assignment in Scala is defined using the keyword 'val' or 'var'.
  // `val` (Values) are immutable, that means they cannot change once they are assigned.
  // `var` (Variables) are mutable, that means they can be made to reference a different value.

  // The type of a value is inferred by the Scala compiler.
  // In this example, the Scala compiler infers that the type of `youAreLearning` is String.
  val youAreLearning = "Scala"

  // The type of a value can also be explicitly specified using a colon and the type name.
  val youAreAwesome: Boolean = true

  // Values can be eager or lazy.
  //
  // Eager values are evaluted when the containing object is created. The two previous examples
  // are computed when the `Variables` object is created.
  //
  // Lazy values are evaluated only when they are first accessed. Lazy values are useful when
  // computation is expensive or when the value is not needed until later in the program.
  lazy val someExpensiveComputation = {
    // some expensive computation here
    42
  }

  // On the following exercises, replace the `???` placeholder with the correct code.
  // Exercise: Assign your age to the `yourAge` variable.
  lazy val yourAge: Int = ???

  // Most of the time immutable variables are the kind you will be using on Scala,
  // However there are special occasions when you actually need *mutable references*
  // IMPORTANT: As noted before these type of variables are discouraged from functional programming principles.
  var beers: Int = 0
  beers += 1
  beers = beers * 2
  // Exercise: Make the beers variable to hold a value of 99

  // Caveat: Values can have internal mutable variables
  //
  // As such even if something appears as an immutable val it could still mutate! Dx
  val rodeo: MutableSeq[Int] = MutableSeq(0)
  // Exercise: Using MutableSeq#update, change the value of rodeo to be positive
  def updateRodeo(): Unit = rodeo.update(0, ???)
}
