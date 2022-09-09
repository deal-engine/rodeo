import zio.{Chunk, Trace, ZIO}
import zio.internal.stacktracer.SourceLocation
import zio.test.Spec.labeled
import zio.test.{
  Spec,
  SuiteConstructor,
  TestAspect,
  TestAspectPoly,
  TestConstructor,
  TestResult,
  suite,
  test
}

import collection.mutable.{ArrayBuffer => MutableSeq}

/** Nothing to see here. Open the chapter files in order.
  *
  * In here just helpers used in chapters.
  */
package object rodeo {

  trait Chapter {
    type SpecType = Spec[Any, Any]

    private val mutableSpecs: MutableSeq[Exercise] = MutableSeq.empty

    def exercises: Chunk[Exercise] = Chunk.fromIterable(mutableSpecs)

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
        into: MutableSeq[Exercise]
    ) {

      def apply(assertion: => TestResult)(implicit
          sourceLocation: SourceLocation
      ): Unit =
        into.addOne(
          new Exercise(
            instruction,
            assertion = ZIO.succeed(assertion),
            enabled,
            sourceLocation,
            aspect
          )
        )

    }
  }

}
