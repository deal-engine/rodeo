package rodeo

object BasicTypes {
  // Ignore this line
  type ??? = Nothing

  // In scala we have a few basic types, which are the same as in Java.
  // For example, we have `Int`s and `Double`s, `Boolean`s, `String`s, `Char`s, you might
  // have seen these before.

  // However Scala also has a few more types, which are not in Java. But they are very useful.
  // For example, we have `Unit`, which is a type with only one value, written as `()`. This is useful
  // for functions that don't return meaningful information, most frequently used for functions
  // performing side-effects but we will se that later.
  // NOTE: In the Computer Sciences lingo, we often refer to functions that return Unit and
  // have side effects as Procedures, meanwhile Functions refers to a more orthodox meaning.
  val unit: Unit = ()

  // The `Any` type is the super type of all types that can possibly exist.
  // This means that an Int is also an instance of `Any`, as is any String or any other type.
  // Exercise: Try changing the right hand side to a String or a Boolean
  val anyValueCanBeAssignedHere: Any = 1

  // Any - Object
  // Common pitfalls

  // We also have `Nothing`, which is a subtype of all other types that can possibly exist.
  // Contrary to `Any`, `Nothing` has no instances. In other words, there exist no values of type `Nothing`.
  // In literature Nothing is called the "bottom type".
  // It is useful for functions that never return, for example when they throw an exception.
  // Or for functions that have an infinite loop like a web server.
  def neverReturns: Nothing = throw new Exception("This function will never return a value since it throws")

  // Event when `Nothing` seems weird, it is very useful in practice. For example, when we want to
  // write a function or value that is not yet implemented, we can use `???` which is a shorthand for
  // `throw new NotImplementedError()` and has the type `Nothing`.
  //
  val computeAllThePrimes: List[Int] = ??? // Still don't know how to do this

}
