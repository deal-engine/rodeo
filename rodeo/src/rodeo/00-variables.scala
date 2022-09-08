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

  // `val` variables are  immutable, that means they cannot change once they are assigned.
  //
  // Exercise: Assign your age to the `yourAge` variable.
  val yourAge: Int = ???

  // Most of the time immutable variables are the kind you will be using on Scala,
  // However there are special occasions when you actually need *mutable references*
  // IMPORTANT: As noted before these type of variables are discouraged from functional programming principles.
  var seen: Int = 0
  seen += 1
  seen += 3
  // Exercise: Make the seen variable to hold a value of 99

  // Caveat: Values can have internal mutable variables
  //
  // As such even if something appears as an immutable val it could still mutate! Dx
  val rodeo: MutableSeq[Int] = MutableSeq(0)
  // Exercise: Using MutableSeq#update, change the value of rodeo to be positive
  rodeo.update(0, ???)
}