package rodeo

import zio.test.assertTrue

object Options extends Chapter {

  // Options are used to represent the presence or absence of some value.
  //
  // Some other non-functional languages use NULL to indicate absence.
  // However this can lead to unchecked errors, since people cannot know
  // if the value exists unless they explicitly check with `!= null`
  //
  // For this reason, in Scala it's a common idiom to use the Option type instead.
  // For example: the type Option[String] expresses the idea of an String that may
  // exist or not.

  Exercise(
    "Use the Some constructor to create an Option"
  ) {
    //  Using the Some(_) constructor, change the value assigned to
    // this variable to be some integer 42.
    val theAnwserToTheUniverseAndEverything: Option[Int] = None

    assertTrue(theAnwserToTheUniverseAndEverything.contains(42))
  }

  // since optional values could be absent, trying to obtain the contained value
  // is an *unsafe* operation UNLESS you provide a default in case the option is None
  //
  Exercise("Use getOrElse") {
    //  Using Option.getOrElse obtain the value contained in `rodeo` or if it is absent "its my first rodeo"
    def fury(rodeo: Option[String]): String = ???

    assertTrue(fury(None) == "its my first rodeo") &&
    assertTrue(fury(Some("My second rodeo")) == "My second rodeo")
  }

  // Option is one of the simplest pure functional data structures
  //
  // It is a great opportunity to take a look at pattern matching!
  //
  // Pattern matching allows us to "switch" depending on the
  // structure of the value it is passed.
  Exercise("Pattern matching on Option") {
    // Change this code so that if theAnswer
    // if theAnswer holds 42 the value returned is "you know it all".
    // if theAnswer holds 23 the value should be "Air Jordan"
    // otherwise the value returned should be "Something"
    def youKnow(theAnswer: Option[Int]): String = theAnswer match {
      case None => "Not enough information"
      case _    => "Fix me"
    }

    assertTrue(youKnow(None) == "Not enough information") &&
    assertTrue(youKnow(Some(42)) == "you know it all") &&
    assertTrue(youKnow(Some(23)) == "Air Jordan") &&
    assertTrue(youKnow(Some(99)) == "Something") &&
    assertTrue(youKnow(Some(10)) == "Something")

  }

  Exercise("Define an empty option of a specific type") {
    // Hint: .isEmpty method documentation contains the definition of "empty"
    val emptyMaybeString: Option[String] = ???
    assertTrue(emptyMaybeString.isEmpty)
  }

  Exercise("Multiple Option operators") {
    // The Option constructor will create Some if the argument is not null
    val myOption = Option(2)
    // .exists returns true if the option is Some(x) and if the inner value fulfills the
    // defined bollean expression
    // Place any predicate that the previous option could fulfill:
    assertTrue(myOption.exists(???))
    // .contains allow us to extract the inner value within Some and perform a direct equivalence
    // comparison. If None, it will return false
    assertTrue(myOption.contains(???))
    // forall returns true when the predicate is applied to the option's value
    // similar to .contains, but caution: forall will return true when empty
    assertTrue( myOption.forall(_ * 2 == 4)  )
    // map allows to transform the Some value into another option
    assertTrue(myOption.map(_.toString) == ??? )
    assertTrue(myOption.map(_ == 2) == ??? )
    // flatMap is slightly different: returns the transformed value but without the Some() that contains it
    assertTrue(myOption.flatMap(???) == "The number is 12" )
  }

  Exercise("Pattern match with Options") {
    // define an option that will satisfy the subsequent match:
    val maybeName: Option[String] = ???
    // The options can be compared more in depth with a match statement,
    // defining which are the valid cases
    maybeName match {
      case Some(name) =>
        assertTrue(name == "raka")
      case None =>
        assertTrue(false)
    }
  }

  Exercise("Convert an Option to a Seq") {
    val maybeName = Some("raka")
    // Use .toSeq to convert an Option to a Seq
    val nameSeq: Seq[String] = ???
    assertTrue(nameSeq == Seq("raka"))
  }
}
