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

  // A variable in Scala is defined using the keyword 'val' or 'var'.
  // `val` variables are  immutable, that means they cannot change once they are assigned.
  // `var` variables are mutable, that means they can be made to reference a different value.

  // The type of a variable is inferred by the Scala compiler.
  // In this example, the Scala compiler infers that the type of `youAreLearning` is String.
  val youAreLearning = "Scala"

  // The type of a variable can also be explicitly specified using a colon and the type name.
  val youAreAwesome: Boolean = true

  // On the following exercises, replace the `???` placeholder with the correct code.
  // Exercise: Assign your age to the `yourAge` variable.
  val yourAge: Int = ???

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
  rodeo.update(0, ???)
}