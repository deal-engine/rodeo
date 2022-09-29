package rodeo

import zio.test._

object Sequences extends Chapter {

  /*
  - head
  - headOption
  - concat
  - pattern matching
  -
   */
  Exercise(
    "...."
  ) {
    val sequence: Seq[Int] = ???
    assertTrue(sequence.sum == 6)
  }

  Exercise("Pattern match") {
    val Seq(one, two): Seq[Int] = ???
    assertTrue(one == 1, two == 2)
  }

  Exercise("Prepend, append, concatenate") {
    val numbers: Seq[Int] = Seq(1, 2)

    val moreNumbers = numbers ++ ???
    assertTrue(moreNumbers == Seq(1, 2, 3, 4))

    val moreAndMoreNumbers = moreNumbers :+ ???
    assertTrue(moreNumbers == Seq(1, 2, 3, 4, 5))

    val moreAndMoreAndMoreNumbers = ??? +: moreAndMoreNumbers
    assertTrue(moreNumbers == Seq(0, 1, 2, 3, 4, 5))
  }

  Exercise("Immutability") {
    val numbers: Seq[Int] = Seq(1, 2)
    val moreNumbers = numbers.updated(0, 3)

    assertTrue(moreNumbers == Seq(3, 2))
    // What is the current value of `numbers`? Did it change?
    assertTrue(numbers == ???)
  }

  Exercise("map and flatMap") {
    val numbers: Seq[Int] = Seq(1, 2, 3)
    val duplicates: Seq[Int] = numbers.map(number => ???)
    val triplicates: Seq[Int] = numbers.map { number =>
      ???
    }

    assertTrue(duplicates == Seq(2, 4, 6))
    assertTrue(triplicates == Seq(6, 12, 18))

    val twins: Seq[Int] = numbers.flatMap(number => ???)
    val triplets: Seq[Int] = numbers.flatMap { number =>
      ???
    }
    assertTrue(twins == Seq(1, 1, 2, 2, 3, 3))
    assertTrue(triplicates == Seq(1, 1, 1, 2, 2, 2, 3, 3, 3))
  }
}
