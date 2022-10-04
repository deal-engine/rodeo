package rodeo

import zio.test._

object Sequences extends Chapter {
  /*
    In this chapter we will study an important trait called Sequence. A sequence can have several different implementations
    and the one we will use the most is List. In Scala a List is a Sequence that is implemented as an immutable linked list.
    An important observation is that when we define a new sequence in Scala this sequence is automatically implemented as a List.
    We can a have other different implementations of a Sequence, like a Vector or an Array but we will  use them only as
    needed.

  */

  /*
    In the following exercises we will introduce some of the most common methods defined for Sequences.
    */


  /*
    The head method returns the first element in a given Sequence. The tail method returns the Sequence that remains
    after deleting the first element.

    */

  Exercise("head & tail") {
    val sequence: Seq[Int] = Seq(1,2,3,7)
    assertTrue(sequence.head == ???)
    assertTrue(sequence.tail == ???)
    assertTrue(sequence.tail.tail == ???)
  }

  /*
   In scala we can construct an empty Seq with Nil. The headOption method can be important in cases like this.
    */

  Exercise("headOption") {
    val sequence: Seq[Int] = Nil
    assertTrue(sequence.headOption == ???)
    // What would happen if we used sequence.head instead ?
  }

  // The sum method can be used to calculate the sum of the elements in given Sequence.

  Exercise("sum") {
    val sequence: Seq[Int] = ???
    assertTrue(sequence.sum == 6)
  }

  // To find the length of a Sequence we only need to use the length method.

  Exercise("length") {
    val luck: Seq[Int] = ???
    assertTrue(luck.length == 7)
  }


  // If we want to find the index of the first appearance of an element in a Sequence we can use the indexOf method.

  Exercise("index") {
    val smallPrimes: Seq[Int] = Seq(2,3,5,7)
    val smallSquares:Seq[Int] = ???
    assertTrue(smallPrimes.indexOf(7) == ???)
    assertTrue(smallSquares.indexOf(4) == 1)
  }

  // To check if a given list contains a certain element we use the `contains` method that a returns a `Boolean`.

  Exercise("contains") {
    val luckNumbers: Seq[Int] = Seq(7,11,13)
    assertTrue(luckNumbers.contains(???))
  }


  Exercise("Prepend, append, concatenate") {

    /*
       In Scala we can concatenate two Sequences using the ++ method. We can also add an element an element to an existing
       Sequence; at the begging or the end by use of the +: method. corregir eso
        */

    val numbers: Seq[Int] = Seq(1, 2)

    val moreNumbers = numbers ++ Seq(3, 4)
    assertTrue(moreNumbers == Seq(1, 2, 3, 4))

    val moreAndMoreNumbers = moreNumbers ++ Seq(5)
    assertTrue(moreAndMoreNumbers == Seq(1, 2, 3, 4, 5))

    val moreAndMoreAndMoreNumbers = 0 +: moreAndMoreNumbers
    assertTrue(moreAndMoreAndMoreNumbers == Seq(0, 1, 2, 3, 4, 5))
  }


  /*
    In a pattern matching a left-hand side pattern is matched against a right-hand side term.
    In the following example we will "match" one with 1 and two with 2.
*/

  Exercise("Pattern match") {
    val Seq(one, two): Seq[Int] = Seq(1, 2)
    assertTrue(one == 1, two == ???)
    val head::tail = Seq(1,3,5,7)
    assertTrue(head == ???, tail == ???)
  }



  // In the following exercises we will introduce others ways of constructing a Sequence in Scala.

  /*
    The Seq.range constructor allows us to construct a Sequence with a given range of numbers.
    In addition to this we can also pass a third argument to this constructor in order to define a "step" value.
  */

  Exercise("Seq.range") {
    val range = Seq.range(1,10)
    val range_2 = Seq.range(1, 5, ???)
    assertTrue(range == ???)
    assertTrue(range_2 == List(1,3))
  }

  // We can use the Seq.fill constructor to create a constant Sequence of a desired length.

  Exercise("Seq.fill") {
    val lucky = Seq.fill(???)("lucky")
    assertTrue {
      lucky == List("lucky", "lucky", "lucky", "lucky", "lucky", "lucky", "lucky")
    }

  }

  Exercise("Immutability") {
    val numbers: Seq[Int] = Seq(1, 2)
    val moreNumbers = numbers.updated(0, 3)

    assertTrue(moreNumbers == Seq(3, 2))
    // What is the current value of `numbers`? Did it change?
    assertTrue(numbers == Seq(1, 2))
  }


  /*
      In the following exercises we will introduce the map and flatMap methods. The map method takes a
      collection A and transforms it into a collection B by applying a map(function) f.
      The flatMap method is similar but you need to do one more step. After applying the function f we also need to
      "break" all the inner "bags" apart. This process can be formalized using the flatten method.

  */

  Exercise("map and flatMap") {
    val numbers: Seq[Int] = Seq(1, 2, 3)
    val duplicates: Seq[Int] = numbers.map(number => 2* number)
    val triplicates: Seq[Int] = duplicates.map { number =>
      3*number
    }

    assertTrue(duplicates == Seq(2, 4, 6))
    assertTrue(triplicates == Seq(6, 12, 18))

    val twins: Seq[Int] = {
      numbers.flatMap(number => Seq(number,number))
    }
    val triplets: Seq[Int] = numbers.flatMap { number =>
      Seq(number,number,number)
    }
    assertTrue(twins == Seq(1, 1, 2, 2, 3, 3))
    assertTrue(triplets == Seq(1, 1, 1, 2, 2, 2, 3, 3, 3))
  }

  /*
  Now we will switch attention to the filter and forall methods. The filter method takes a collection A and returns  the
  elements a A that satisfy a certain condition(predicate). The forall method can be used to check if all the
  elements of a collection satisfy a required condition. It will return true only in the case where all the elements of the
  collection satisfy the given predicate and false in any other case.
   */

  Exercise("filter"){
    val numbers: Seq[Int] = Seq(3, 4, 5, 6, 7, 8)
    val filtered_numbers = numbers.filter(_<6)
    val triples : Seq[Int] = numbers.filter(_<6).map(x => ???)
    assertTrue(filtered_numbers == ???)
    assertTrue(triples == Seq(9,12,15))
  }

  Exercise("forall") {
    val numbers: Seq[Int] = Seq(3, 9, 12, 21)
    val empty: Seq[Int] = Nil
    val result  = numbers.forall(x => {x % 3 == 0})
    val vacuity = empty.forall(x => {x > x} )
    assertTrue(result != ???)
    assertTrue(vacuity == ???)
  }

  /*
    The find method takes a predicate(condition) and returns an Option of the first element of the Sequence
    that satisfies the given condition. In the case where no element in our Sequence satisfies the condition it will
    return None.
     */

  Exercise("find") {
    val boringnumbers: Seq[Int] = Seq(1, 2, 4, 6, 8, 9)
    val resultOption = boringnumbers.find(x => {x%3==0})
    val resultNone = boringnumbers.find(x => {x%5==0})
    assertTrue(resultOption == ???)
    assertTrue(None == ???)
  }


  /*
    If we want to see if some element of a given sequence satisfies a certain predicate we can use the
     Boolean valued exists method. This method will return true in the case where there exists an element in our
     sequence  that satisfies the given predicate and false in any other case.
    */

  Exercise("exists") {
    val boringnumbers: Seq[Int] = Seq(1, 2, 4, 6, 8, 9)
    val exists_result = boringnumbers.exists(x => {x % 3 == 0 && x % 2 ==0})
    assertTrue(exists_result)
 }


  /*
   The next method we we want to introduce is the fold method. This method is best understood be first considering a
   simple example. Lets say we have Sequence val Y = Seq(1,2,3,4,5) and we want to find the product of all the elements
   of this sequence recursively. We can achieve this by defining x as the "accumulator" of the multiplication
   process and doing the following steps:

   Step 1: x(1):=x(0)*Y(1)=1*1=1
   Step 2: x(2):=x(1)*Y(2)=1*2=2
   Step 3: x(3):=x(2)*Y(3)=2*12=6
   Step 4: x(4):=x(3)*Y(4)=4*6=24
   Step 5: x(5):=x(4)*Y(5)=24*5=120


  In Scala we implement this as Y.fold(1)((x,y) => x*y ). As we can see with this example the fold method takes two
  arguments; the "start" value and a function; this function also takes two arguments; the accumulator and the "current"
  value of the sequence y.
   */

  Exercise("fold") {
    val Y: Seq[Int] = Seq(1, 2, 3, 4, 5)
    val product = Y.fold(1)((x,y) => x*y)
    val sum = Y.fold(???)((x,y) => x+y)
    assertTrue(product == ???)
    assertTrue(sum == 15)
  }

// We can also transform Options with all the functional methods we have seen before.
  Exercise("transforming options with flatten") {

    val numberOptions = List(Some(9),Some(5),Some(7),None)
    val numbersFlat = numberOptions.flatten

    assertTrue(numbersFlat == ???)
  }


  Exercise("transforming options with map") {
    // The map method transforms an Option into a new Option.
    // If the original Option was empty the map method will return nothing.
    // If the original Option had a value the map method will work the same as before.

    val numbersOpt = List(Some(5), Some(7), None)
    val numbersOpts2 = numbersOpt.map{
      case None => None
      case Some(n) => Some(n * 2)
    }

    assertTrue(numbersOpts2 == ???)
    assertTrue(numbersOpts2.flatten == ???)

  }


  Exercise("transforming options with flatMap") {

    val numbersOpt = List(Some(5), Some(7), None)
    val numbersOpts2 = numbersOpt.flatMap{
      case None => None
      case Some(n) => Some(n * 2)
    }

    assertTrue(numbersOpts2 == ???)
  }


}



