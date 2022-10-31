package rodeo

import zio.test.TestAspect._
import zio.test._
import zio._

object RodeoSpec extends ZIOSpecDefault {

  override def spec: Spec[Any, Any] =
    (
      suite("Scala Basics")(
        suite("Variables")(Variables.exercises),
        suite("Tuples")(Tuples.exercises),
        suite("Options")(Options.exercises),
        suite("Sequences")(Sequences.exercises),
        suite("Function")(Functions.exercises),
        suite("Implicits")(Implicits.exercises)
      ) @@ sequential @@ failFast
    ).provideLayerShared(Latch.live)

  type Latch = Ref[Option[Unit]]
  object Latch {
    val live = {
      ZLayer.fromZIO(
        Ref.make(Option.empty[Unit])
      )
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
