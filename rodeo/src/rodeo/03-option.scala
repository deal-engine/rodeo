package rodeo

object Options {

  // Options are used to represent the presence or absence of some value.
  //
  // Some other non-functional languages use NULL to indicate absence.
  // However this can lead to unchecked errors, since people cannot know
  // if the value exists unless they explicitly check with `!= null`
  //
  // For this reason, in Scala it's a common idiom to use the Option type instead.
  // For example: the type Option[String] expresses the idea of an String that may
  // exist or not.

  // Exercise: Using the Some(_) constructor, change the value assigned to
  // this variable to be some integer 42.
  val theAnwserToTheUniverseAndEverything: Option[Int] = None

  // Option is one of the simplest pure functional data structures
  //
  // It is a great opportunity to take a look at pattern matching!
  //
  // Pattern matching allows us to "switch" depending on the
  // structure of the value it is passed.
  // Exercise: Change this code so that if theAnswerToTheUniverseAndEverything
  // holds some value, the `youKnow` variable gets assigned to "you know it all".
  val youKnow: String = theAnwserToTheUniverseAndEverything match {
    case None         => "you know nothing"
    case Some(answer) => ???
  }


  // since optional values could be absent, trying to obtain the contained value
  // is an *unsafe* operation UNLESS you provide a default in case the option is None
  //
  // Excercise: Using Option.getOrElse obtain the value contained in `rodeo` or if it is absent "its my first rodeo"
  def fury(rodeo: Option[String]): String = ???

}
