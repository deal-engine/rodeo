import zio.internal.stacktracer.SourceLocation
import zio.test.{
  Spec,
  TestAspect,
  TestAspectPoly,
  TestConstructor,
  TestResult,
  test
}
import zio.{Chunk, ZIO}

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
        instruction: String,
        pending: Boolean = false,
        aspect: TestAspectPoly = TestAspect.identity
    ): rodeo.Exercise.PartiallyApplied =
      new rodeo.Exercise.PartiallyApplied(
        instruction,
        !pending,
        aspect,
        mutableSpecs
      )
  }

  case class Exercise(
      instruction: String,
      assertion: ZIO[Any, Nothing, TestResult],
      enabled: Boolean,
      sourceLocation: SourceLocation,
      aspect: TestAspectPoly
  )

  object Exercise {

    class PartiallyApplied(
        instruction: String,
        enabled: Boolean,
        aspect: TestAspectPoly,
        into: MutableSeq[SpecType]
    ) {
      def apply(spec: SpecType): Unit = {
        into.addOne(spec.when(enabled) @@ aspect)
      }

      def apply(assertion: ZIO[Any, Nothing, TestResult])(implicit
          sourceLocation: SourceLocation
      ): Unit = into.addOne(
        test(instruction)(assertion)(
          implicitly[TestConstructor[Any, ZIO[Any, Nothing, TestResult]]],
          sourceLocation,
          implicitly
        ).when(enabled) @@ aspect
      )

      def apply(assertion: => TestResult)(implicit
          sourceLocation: SourceLocation
      ): Unit = apply(ZIO.succeed(assertion))(sourceLocation)

    }
  }

}
