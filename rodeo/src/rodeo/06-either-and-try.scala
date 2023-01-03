package rodeo
import zio.test._

object EitherAndTry extends Chapter {

  import scala.util.{Try, Success, Failure}

  // The `Try[A]` type is similar to `Option[A]`. It is used to represent the type of a computation that attempts to return an instance of type A.
  // If the computation succeeds we obtain an instance of type `Success[A]`. If it fails we obtain a wrapped instance of the Throwable class. This instance will have type `Failure`.
  // The Throwable class is the least derived class of the Scala exception hierarchy. An instance of this class can represent an error or an exception.
  // For practical purposes the main difference between `None` and `Failure` is that `Failure` is able to provide insight into the cause of the "failure".

  Exercise("Try constructor") {
    // The Try constructor can build a valid Success if given a valid expression, otherwise, it will return a Failure
    assertTrue(Try(7) == Success(???))
    // what happens if a number is divided by 0? will it throw an specific exception?
    assertTrue(Try(10 / 0).isFailure == ???)
  }

  Exercise("Books") {
    // This method will try to recommend the user a book based on his/her tastes, but it will fail if the if tastes are contradictory
    def bookRecomendation(
        likesMath: Boolean,
        likesComputerScience: Boolean
    ): Try[String] =
      if (!likesMath && !likesComputerScience)
        Failure(
          new IllegalArgumentException("You need to go visit a quantum doctor")
        )
      else if (likesMath && !likesComputerScience)
        Failure(new IllegalArgumentException("You should not be reading this"))
      else if (!likesMath && likesComputerScience)
        Failure(
          new IllegalArgumentException(
            "I am unable to give you a recommendation"
          )
        )
      else Success("The emperors new mind")
    // what happens if you don't like math, but you like CS?
    assertTrue(bookRecomendation(false, true) == ???)
    assertTrue(bookRecomendation(true, true) == ???)
  }

  Exercise("SquareRootTry") {
    // This method will try to calculate the square root of a number
    def sqrt(x: Double): Try[Double] =
      if (x < 0)
        Failure(
          new IllegalArgumentException("Negative numbers don't have a square root in the real numbers")
        )
      else Success(Math.sqrt(x))
    // TODO easy, right?
    assertTrue(sqrt(4) == Success(???))
    // what happens if the user tries to find the square root of a negative number?
    assertTrue(sqrt(-1).isSuccess == ???)
  }

  Exercise("Party") {
    // Sometimes you have to make tough decisions when having a party because the number of people must be limited
    // when said party is small
    case class Friend(name: String, isClose: Boolean)

    val ana = Friend("Ana", true)

    case class Party(title: String, isBig: Boolean)

    val christmasParty = Party("Christmas", true)

    case class Invitation(friend: Friend, party: Party)

    def invitation(friend: Friend, party: Party): Try[Invitation] = {
      if (friend.isClose) Success(Invitation(friend, party))
      else if (party.isBig) Success(Invitation(friend, party))
      else
        Failure(
          new IllegalStateException(
            s"Friend ${friend.name} is not a close friend and this is a small party"
          )
        )
    }
    // will ana get invited to the christmas party, considering the party conditions?
    assertTrue(invitation(ana, christmasParty) == ???)

  }

  Exercise("map method on Try") {

    // We can define functional methods in Try[A] like map, filter and flatmap.
    // This methods behave in almost the same way as they do in the case of
    // Options.

    val names = List(
      Success("Apple"),
      Success("Orange"),
      Success("Avocado"),
      Failure(new IllegalArgumentException("Potato")),
      Failure(new IllegalArgumentException("Carrot"))
    )
    val namesOffice = names.map {
      case Failure(_) => "Not a fruit!"
      case Success(name) => Success(name + " is a fruit.")
    }

    assertTrue(namesOffice(1) == ???)
  }

  // We will now introduce the `Either[A,B]` type.
  // An instance of the `Either[A,B]` type is either an instance of type A or an instance of type B. In mathematical terms `Either[A,B]` is the disjoint union of A and B, it is defined as a sum type.
  // `Either` can be used in cases where a computation could return two different types depending on a certain condition imposed in the input.
  // The `Either[A,B]` type is more general than the `Try[A]` type. Try[A] = Either[Failure[A],Success[A]]`.
  // One difference in the case of exception handling between Try and Either is that by using Either we are not forced to represent failure by a wrapped instance of the Throwable class.
  // We can understand `Either[A,B]` intuitively as a choice between left and right. For exception handling, left represents failure while right represents success.

  Exercise("Tacos") {
    // This function tries to calculate the optimal number of tacos you can eat based on your weight and physical condition
    // you may consume a greater amount of tacos if you are in a good condition (just a recommendation).
    def numberOfTacos(
        weight: Double,
        exercised: Boolean
    ): Either[String, Double] =
      if (weight < 0) Left("This is not a valid weight")
      else Right(if (exercised) 2 * (weight / 20) else weight / 20)
    // what? negative mass?
    assertTrue(numberOfTacos(-1, false) == ???)
    assertTrue(numberOfTacos(80, false) == ???)
    assertTrue(numberOfTacos(80, true) == ???)
  }

  Exercise("SquareRootEither") {
    // Based on the previous Square root exercise, this is the Either-adapter version, sho it should be pretty similar.
    def sqrt(x: Double): Either[String, Double] =
      if (x < 0)
        Left("Negative numbers don't have a square root in the real numbers")
      else Right(Math.sqrt(x))

    assertTrue(sqrt(4) == ???)
    assertTrue(sqrt(-1) == ???)

  }

  // Either[A,B] type can be useful in other cases than exception handling.

  Exercise("MixedDuplicator") {
    // This method can candle String and Double multiplications (* operators) although they might behave different
    // from each other
    def mixedDuplicator(x: Either[String, Double]): Either[String, Double] = {
      x match {
        case Left(x)  => Left(x * 2)
        case Right(x) => Right(x * 2)
      }
    }
    assertTrue(mixedDuplicator(Right(7)) == ???)
    // just because you're correct doesn't mean you're right
    assertTrue(mixedDuplicator(Left("Hi")) == ???)
  }

  // We can also define functional methods in `Either[A,B]` like `map` and `flatMap`
  // This functional methods are “right biased”. They will transform the Right case only and leave the Left case fixed.

  Exercise("Map method on Either") {

    assertTrue(Right(7).map((x: Int) => x * x) == ???)
    // Remember that .map is right-biased
    assertTrue(Left("Hello").map((x: String) => x + x) == ???)
  }

  // flatMap is almost like map, but it allows us to chain operations on multiple Either[A, B].
  
  Exercise("FlatMap method on Either") {

    case class SomeError() 

    // Safely get the division as an integer if possible.
    // We get the modulus to ensure that the division is an integer.
    def divInt(x: Int, y: Int): Either[SomeError, Int] = y match {
      case 0 => Left(SomeError())
      case _ => (x % y) == 0 match {
        case true => Right(Math.floorDiv(x, y))
        case false => Left(SomeError())
      }
    }

    assertTrue(divInt(4,0).flatMap(divInt(_,4)) == ???)
    assertTrue(divInt(4,2).flatMap(divInt(_,2)) == ???)
  }

  // `Either` has a `filterOrElse` method that transforms a `Right` value into a `Left` value if this value does not satisfy a given predicate(condition).

  Exercise("filterOrElse") {
    // is 14 divisible by 2 and 7?
    assertTrue(
      Right(14).filterOrElse(
        x => (x % 2 == 0 && x % 7 == 0),
        "Not divisible by 2 and 7"
      ) == ???
    )
    // what about 21? would that transform it to a Left?
    assertTrue(
      Right(21).filterOrElse(
        x => (x % 2 == 0 && x % 7 == 0),
        "Not divisible by 2 and 7"
      ) == ???
    )
  }

  // We can also get an Option[A] directly from a Either[B,A].
  Exercise("Either to Option") {
    // Right => Some
    assertTrue(Right(42).toOption == ???)
    // Left => ???
    assertTrue(Left("Evil value >:D").toOption == ???)

    // Or even with try:
    val some = Try(42 / 0)
    assertTrue(some.toEither == ???)
    assertTrue(some.toEither.toOption == ???)
  }

}
