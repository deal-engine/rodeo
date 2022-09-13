import zio.internal.stacktracer.SourceLocation
import zio.test.{
  Spec,
  TestAspect,
  TestAspectAtLeastR,
  TestAspectPoly,
  TestConstructor,
  TestResult,
  test
}
import zio.{Chunk, Trace, ZIO}

import scala.collection.mutable.{ArrayBuffer => MutableSeq}

/** Nothing to see here. Open the chapter files in order.
  *
  * In here just helpers used in chapters.
  */
package object rodeo {
  type SpecType = Spec[Any, Any]

  trait Chapter {
    private val mutableSpecs: MutableSeq[SpecType] = MutableSeq.empty

    def exercises: Chunk[SpecType] = Chunk.fromIterable(mutableSpecs)

    def Exercise(
        name: String,
        aspect: TestAspectPoly = TestAspect.identity
    ): rodeo.Exercise.PartiallyApplied =
      new rodeo.Exercise.PartiallyApplied(
        name,
        aspect,
        mutableSpecs
      )
  }

  case class Exercise(
      name: String,
      assertion: ZIO[Any, Nothing, TestResult],
      sourceLocation: SourceLocation,
      aspect: TestAspectPoly
  )

  object Exercise {

    def pendingAspect(pending: Boolean): TestAspectAtLeastR[Any] =
      new TestAspectAtLeastR[Any] {
        override def some[R <: Any, E](
            spec: Spec[R, E]
        )(implicit trace: Trace): Spec[R, E] =
          spec.transform[R, E] {
            case Spec.LabeledCase(label, spec) if pending =>
              Spec.LabeledCase(s"TODO: ${label}", Spec.empty)
            case other => other
          }
      }

    class PartiallyApplied(
        name: String,
        aspect: TestAspectPoly,
        into: MutableSeq[SpecType]
    ) {
      def apply(spec: SpecType): Unit = {
        // TODO Add Failfast aspect
        into.addOne(spec @@ aspect) // @@ pendingAspect(pending)
      }

      def apply(assertion: ZIO[Any, Nothing, TestResult])(implicit
          sourceLocation: SourceLocation
      ): Unit = apply(
        test(name)(assertion)(
          implicitly[TestConstructor[Any, ZIO[Any, Nothing, TestResult]]],
          sourceLocation,
          implicitly
        )
      )

      def apply(assertion: => TestResult)(implicit
          sourceLocation: SourceLocation
      ): Unit = apply(ZIO.succeed(assertion))(sourceLocation)

    }
  }

}
