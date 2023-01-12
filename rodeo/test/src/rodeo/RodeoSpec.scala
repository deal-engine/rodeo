package rodeo

import zio.test.TestAspect._
import zio.test._
import zio._
import zio.Cause.Die

object RodeoSpec extends ZIOSpecDefault {

  override def spec: Spec[Any, Any] =
    (
      suite("Scala Basics")(
        suite("Variables")(Variables.exercises),
        suite("Tuples")(Tuples.exercises),
        suite("Functions And Methods")(FunctionsAndMethods.exercises),
        suite("Polymorphic Functions")(PolymorphicFunctions.exercises),
        suite("Options")(Options.exercises),
        suite("Sequences")(Sequences.exercises),
        suite("Either & Try")(EitherAndTry.exercises),
        suite("Functions Leftovers")(FunctionsLeftovers.exercises),
        suite("Case Classes")(CaseClasses.exercises),
        suite("Implicits")(Implicits.exercises),
      ) @@ sequential @@ handleNothing @@ failFast
    ).provideLayerShared(Latch.live)

  type Latch = Ref[Option[Unit]]
  object Latch {
    val live = {
      ZLayer.fromZIO(
        Ref.make(Option.empty[Unit])
      )
    }
  }

  def handleNothing =
    new TestAspect.PerTest {
      def perTest[R, E](test: ZIO[R,TestFailure[E],TestSuccess])(implicit trace: Trace): ZIO[R,TestFailure[E],TestSuccess] = {
        test.mapError {
          case err@TestFailure.Runtime(cause: Cause[Nothing], _) => cause match {
            case Die(value, trace) => {
              println(trace)
              TestFailure.Runtime(Cause.die(value))
            }
            case otherwise => err
          }
          case otherwise => otherwise
        }
      }
    }

  def failFast =
    new TestAspect.PerTest.AtLeastR[Latch] {
      def perTest[R <: Latch, E](test: ZIO[R, TestFailure[E], TestSuccess])(
          implicit trace: Trace
      ): ZIO[R with Latch, TestFailure[E], TestSuccess] = {
        for {
          latch <- ZIO.service[Latch]
          hasFailed <- latch.get
          res <-
            if (hasFailed.isDefined)
//              Annotations.annotate(TestAnnotation.tagged, Set("SKIPPED")) *>
              ZIO.succeed(TestSuccess.Ignored())
            else
              test
                .onError(_ => latch.set(Some()))
        } yield res
      }
    }
}
