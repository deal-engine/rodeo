package rodeo

import zio.test.assertTrue

object Tuples extends Chapter {

  // Tuples are one of the basic structures in scala that allow you to group
  // several values into one.
  //
  // Each element of a tuple can be of different type, for example, the
  // tuple containing an String and an Int is of type: Tuple2[String, Int]
  //
  // In Scala2, tuples can hold from one to 22 different values.
  //
  // In Scala2, there is syntactic sugar to shorten those types:
  //   - Tuple1[Int] = (Int)
  //   - Tuple2[Int, Boolean] = (Int, Boolean)
  //   - Tuple3[Int, Boolean, Double] = (Int, Boolean, Double)
  //   - ...
  //
  // In Scala3 tuples are not restricted to 22 fields.

  Exercise("Create a tuple containing your name and age") {
    val nameAndAge: (String, Int) = (???, ???)
    assertTrue(nameAndAge._1.nonEmpty, nameAndAge._2 > 0)
  }

  // Accessing tuple contained elements.
  //
  // Once you have a tuple, you can access its internal elements by
  // using the _<position> method provided by Scala 2.
  //
  // eg. tuple._1 should give you access to the first value
  // and tuple._2 to the second one, and so on.
  //
  // In Scala 3 this is different and they are accessed with normal
  // indexing: i.e. tuple(0) for the first value

  Exercise("Access the second element of a tuple") {
    val friends: (String, String) = ("davo", "fabian")
    // Using Tuple._2 access the second element of the `friends` tuple and
    // assign it to the `secondFriend` variable.
    val secondFriend: String = ???

    assertTrue(secondFriend == "fabian")
  }

  Exercise("Use arrow `->` as syntactic sugar to create tuples") {
    val friends: (String, String) = "TODO" -> "TODO"
    assertTrue(friends == ("davo", "fabian"))
  }
}
