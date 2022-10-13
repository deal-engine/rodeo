package rodeo
import zio.test._

object EitherAndTry extends Chapter {

  import scala.util.{Try, Success, Failure}

  // The `Try[A]` type is similar to `Option[A]`. It is used to represent the type of a computation that attempts to return an instance of type A.
  // If the computation succeeds we obtain an instance of type `Success[A]`. If it fails we obtain a wrapped instance of the Throwable class. This instance will have type `Failure`.
  // The Throwable class is the least derived class of the Scala exception hierarchy. An instance of this class can represent an error or an exception.
  // For practical purposes the main difference between `None` and `Failure` is that `Failure` is able to provide insight into the cause of the "failure".

  Exercise("Try constructor") {
    assertTrue(Try(7) == Success(???))

    // Delete the following line if you can't find the answer.

    assertTrue(Try(10 / 0).getClass == ???)
  }

  Exercise("Books") {
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

    assertTrue(bookRecomendation(false, true) == ???)
    assertTrue(bookRecomendation(true, true) == ???)
  }

// To find a solution of the quadratic equation ax^2+bx+c=0 we only need to use the formula  x = (-b + sqrt(b^2-4ac))/2a.

  Exercise("Equation") {
    def quadraticEquationSolver(
        a: Double,
        b: Double,
        c: Double
    ): Try[Double] = {
      def discriminant(a: Double, b: Double, c: Double): Double =
        Math.pow(b, 2) - 4 * a * c
      if (discriminant(a, b, c) >= 0)
        Success((-b + Math.sqrt(discriminant(a, b, c))) / 2 * a)
      else if (a == 0)
        Failure(
          new IllegalArgumentException("This is not a quadratic equation")
        )
      else
        Failure(
          new IllegalArgumentException(
            "This equations does not have a solution in the real numbers"
          )
        )
    }

    assertTrue(quadraticEquationSolver(0, 7, 13) == ???)
    assertTrue(quadraticEquationSolver(???, ???, ???) == Success(Math.sqrt(2)))
  }

  Exercise("Party") {
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
            "Friend ${friend.name} is not a close friend and this is a small party}"
          )
        )
    }

    assertTrue(invitation(ana, christmasParty) == ???)

  }

  // We can define functional methods in Try[A] like map, filter and flatmap. This methods behave in almost the same way as they do in the case of Options.

  Exercise("map method on Try") {
    val names = List(
      Success("David"),
      Success("Vic"),
      Success("Fabian"),
      Failure(new IllegalArgumentException("Andrew"))
    )
    val namesOffice = names.map {
      case Failure(name) => "Lacayo rebelde"
      case Success(name) => Success(name + "_" + "DealEngine")
    }

    assertTrue(namesOffice(1) == ???)
  }

  // We will now introduce the `Either[A,B]` type.
  // An instance of the `Either[A,B]`type is either an instance of type A or an instance of type B. In mathematical terms `Either[A,B]` is the disjoint union of A and B.
  // `Either` can be used in cases where a computation could return two different types depending on a certain condition imposed in the input.
  // The `Either[A,B]` type is more general than the `Try[A]` type. Try[A] = Either[Failure[A],Success[A]]`.
  // One difference in the case of exception handling between Try and Either is that by using Either we are not forced to represent failure by a wrapped instance of the Throwable class.
  // We can understand `Either[A,B]` intuitively as a choice between left and right. For exception handling, left represents failure while right represents success.

  Exercise("Tacos") {
    def numberOfTacos(
        weight: Double,
        exercised: Boolean
    ): Either[String, Double] =
      if (weight < 0) Left("This is not a valid weight")
      else Right(if (exercised) 2 * (weight / 20) else weight / 20)

    assertTrue(numberOfTacos(80, false) == ???)
  }

  Exercise("SquareRoot") {
    def sqrt(x: Double): Either[String, Double] =
      if (x < 0)
        Left("Negative numbers don't have a square root in the real numbers")
      else Right(Math.sqrt(x))

    assertTrue(sqrt(4) == ???)
    assertTrue(sqrt(-1) == ???)

  }

  // Either[A,B] type can be useful in other cases than exception handling.

  Exercise("MixedDuplicator") {
    def mixedDuplicator(x: Either[String, Double]): Either[String, Double] = {
      x match {
        case Left(x)  => Left(x * 2)
        case Right(x) => Right(x * 2)
      }
    }
    assertTrue(mixedDuplicator(Right(7)) == ???)
    assertTrue(mixedDuplicator(Left("Hi")) == ???)
  }

  // We can also define functional methods in `Either[A,B]` like `map` and `flatMap`
  // This functional methods are “right biased”. They will transform the Right case only and leave the Left case fixed.

  Exercise("Map method on Try") {

    assertTrue(Right(7).map((x: Int) => x * x) == ???)
    assertTrue(Left("Peje").map((x: String) => x + x) == ???)
  }

  // TODO `flatMap` excercise.

  // `Either` has a `filterOrElse` method that transforms a `Right` value into a `Left` value if this value does not satisfy a given predicate(condition).

  Exercise("filterOrElse") {

    assertTrue(
      Right(14).filterOrElse(
        x => (x % 2 == 0 && x % 7 == 0),
        "Not divisible by 2 and 7"
      ) == ???
    )
    assertTrue(
      Right(21).filterOrElse(
        x => (x % 2 == 0 && x % 7 == 0),
        "Not divisible by 2 and 7"
      ) == ???
    )
  }

}
