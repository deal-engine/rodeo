package rodeo

import zio.test.TestAspect._
import zio.test._
import zio.{Chunk, ZIO}

object RodeoSpec extends ZIOSpecDefault {

  def chapterSuite(name: String, chapter: Chapter): Spec[Any, Any] = {
    val testConstructor =
      implicitly[TestConstructor[Any, ZIO[Any, Nothing, TestResult]]]

    val specs: Chunk[Spec[Any, Any]] = chapter.exercises.map { ex =>
      val test = testConstructor(ex.instruction)(ex.assertion)(
        ex.sourceLocation,
        implicitly
      ).when(ex.enabled)

      test @@ ex.aspect
    }
    suite(name)(specs)
  }

  override def spec: Spec[Any, Any] =
    suite("Scala Rodeo")(
      chapterSuite("Variables", Variables),
      chapterSuite("Tuples", Tuples),
      chapterSuite("Options", Options)
    ) @@ sequential
}
