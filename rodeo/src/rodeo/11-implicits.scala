package rodeo

import zio.ZEnvironment
import zio.test._
import scala.util.Random

object Implicits extends Chapter {

  // 1. Implicit parameters/arguments/inputs
  // 2. Implicit conversions (transformations between types)
  // 3. Implicit classes (extension methods)

  // In Scala implicits can have several different meanings.
  // An implicit parameter is a parameter than can be passed to a function without specifying it explicitly.

  Exercise("Implicit parameters") {

    implicit val prefix: String = "Mrs."

    def greeting(name: String, lastName: String)(implicit
        prefix: String
    ): String = {
      s"${prefix} ${name} ${lastName}"
    }

    assertTrue(greeting("Alice", "Gutierrez") == "Mrs. Alice Gutierrez")
    assertTrue(greeting("Mariana", "Zimmerman") == "CHANGE ME!")
  }

  // In the above exercises the compiler was able to handle the implicit input because there was only
  // one `implicit val` defined.

  Exercise("Implicit parameters ambigouous") {
    implicit val two: Double = 2.0
    implicit val three: Double = 3.0

    // As we have two implicits values defined, the compiler can't decide which of this values to use.
    // We have to explicitly give those values.

    def add(numA: Double)(implicit numB: Double): Double = numA + numB

    // Explicity passing the implicit variable `two`.
    assertTrue(add(1)(two) == 3.0)
    assertTrue(add(5)(???) == 9.0) // <- Change this.
  }

  Exercise("Implicit implicits") {
    implicit val three: Double = 3.0

    def add(numA: Double): Double = {
      // `implicitly[A]` allows us to get a value if there is an existing implicit variable of the same
      // type `A`.
      val numB: Double = implicitly[Double]
      numA + numB
    }

    assertTrue(add(1) == Int.???) // Change Int.??? giving the correct value as defined by the function above.
  }

  Exercise("Implicit parameters adjust") {
    // Implicit parameters can be very useful for context-dependend behaviour. We can do a lot of things
    // with it, such as definind a default behaviour for a function or giving instances of certain elements
    // required for execution of actions.
    // In this example we are going to use it to optimize a search function (if possible) over integers.

    type Ix = Int

    def search(xs: List[Int])(v: Int)(implicit mid: Ix => Ix => Option[Ix]): Option[Ix] = {
      def go(l: Ix, r: Ix): Ix = mid(l)(r) match {
        case None => r
        case Some(m) => (v == xs(m)) match {
          case true => go(l, m)
          case false => go(m, r)
        }
      }
      
      xs.isEmpty match {
        case true => None
        case false => Some(go(0, xs.length))
      }
    }

    // All our different methods for mid search
    object SearchMethods {
      def binary(l: Ix)(r: Ix): Option[Ix] = (r - l > 1) match {
        case true => Some((l+r) / 2)
        case false => None
      }

      def linear(l: Ix)(r: Ix): Option[Ix] = (r - l > 1) match {
        case true => Some(l + 1)
        case false => None
      }

      implicit val default: Ix => Ix => Option[Ix] = (a: Ix) => (b: Ix) => linear(a)(b)
    }

    // Default behaviour and optional behaviour.
    import SearchMethods.{default, binary}

    // We have two lists:
    val sortedList   = List(1,2,3,4,5,6,7,8,9)
    val unsortedList = List(2,4,8,6,3,1,7,9,5)

    // Without specifying the implicit parameter, we have the default (linear) search:
    assertTrue(search(sortedList)(4) == Some(3))
    assertTrue(search(unsortedList)(4) == Some(1))
    // This works well, but we have a worst complexity of O(n).
    
    // As we know that `sortedList` is sorted, we can do binary search over it.
    assertTrue(search(sortedList)(4)(binary) == Some(3))
    // Which gives us a worst case complexity of O(log n).

    // C.S. nerd note: both have a best case complexity of Ω(1) since it's possible to get the value in
    // constant time.

    // Find an array
    val xs: List[Int] = ???
    // such that
    assertTrue(search(xs)(25) == None)
    assertTrue(search(xs)(100) == Some(10))
    assertTrue(search(xs.sorted)(42) == Some(4))
    // holds.
    // NOTE: Optimize the search adding the correct implicit where possible.

    // The formal proof for the worst case complexity of both linear and binary search are left as an
    // optional exercise for the reader.
  }

  Exercise("Implicit parameters in a class") {
    // We can also have implicit parameter in class definitions.

    // Let's assume we have a db conenction:
    case class DBConn() {
      def executeSQL(action: String) = "I executed SQL!"
    }

    // We can define services that need connection to the db, but we are able to use it implicitly:
    case class Service(name: String)(implicit dbconn: DBConn) {
      def doSomething = s"Service ${name} from db: ${dbconn.executeSQL("select idk")}"
    }

    // Let's initialize a db connection:
    implicit val _dbconn = DBConn()

    // Now we can build our services:
    val service1 = Service("one")
    val service2 = Service("status")

    // Those services have connection to the db!
    assertTrue(service1.doSomething == "Replace me with the correct output!")
    assertTrue(service2.doSomething == "Replace me with the correct output!")
  }

  // Implicts can also be used in cases where you want to evaluate a function in an argument that is
  // not of the required type.
  // Let's say that we have a recursive function that allows to compute the product of all the
  // elements in a given list and we want to apply this function to a set.
  // We can achieve this by defining an implicit function that takes a set and returns a list.

  Exercise("Implicit conversions") {
    def greeting(
        name: String,
        greetings: Seq[String]
    ): String = greetings.mkString("", s", ${name} -- ", s", ${name}")

    assertTrue(
      greeting("Max", Seq("Hello")) == "Hello, Max"
    )

    implicit def stringToSeq(string: String): Seq[String] = Seq(string)
    // NOTE How greetings is now passing a String, and not a Seq[String] and still works, because of
    // the implicit conversion
    assertTrue(greeting("Max", "Hello") == "Hello, Max")
  }

  Exercise("Implicit conversions 2") {
    case class MexicanPesos(value: Double)
    case class BritishPounds(value: Double)
    implicit def PoundsToPesos(pounds: BritishPounds): MexicanPesos =
      MexicanPesos(pounds.value * 28)

    def mexicanStore(cash: MexicanPesos): String = s"You have spent ${cash.value} pesos, amigo."

    // Fill the exercises:
    assertTrue(mexicanStore(BritishPounds(???)) == "You have spent 56 pesos, amigo.")
    assertTrue(mexicanStore(MexicanPesos(???)) == "You have spent 256 pesos, amigo.")
  }

  // An implicit class can be used in cases where we want to add methods to a class that has already
  // been defined.
  // This could be useful in the following scenarios:
  // 1) You are using a using a library that is not yours and you want to add a method for a class
  // in this library.
  // 2) The method you want to implement does not make sense "globally".

  Exercise("Implicit classes") {
    implicit class ListStat(values: List[Int]) {
      // A function that gets a random value from a list
      def rand: Int = values(Random.between(0, values.length))
    }

    // When an implicit class is in our scope we can call the methods defined inside this class even 
    // this methods are not defined in the type that this class wraps.
    val numbers = List(10, 20, 30)

    val result = numbers.rand // We don't know what we will get!

    assertTrue(List(42).rand == Int.???) // But this case is 'deterministic'.
  }

  Exercise("implicit classes 2") {
    // Implicit classes only take one explicit parameter (the value on which you are adding the
    // extension methods) but can take additional implicit parameters.

    implicit class NumericListOps[A: Numeric](values: List[A]) {
      val numeric: Numeric[A] = implicitly[Numeric[A]]
      def mean: Double = numeric.toDouble(values.sum) / values.length
    }

    val favoriteNumbers: List[Double] = List(10.0, 30.0)
    assertTrue(favoriteNumbers.mean == 20.0)

    // NOTE: Another way of declaring Numeric restriction
    implicit class NumericListOpsTwo[A](values: List[A])(implicit numeric: Numeric[A]) {
      def mean2: Double = numeric.toDouble(values.sum) / values.length
    }

    val favoriteNumbers2: List[Double] = List(10.0, 20.0, 30.0)
    assertTrue(favoriteNumbers2.mean2 == 0.0) // Excercise: Fix me!
  }
}
