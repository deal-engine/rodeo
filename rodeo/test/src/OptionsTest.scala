package rodeo

import zio._
import zio.test._
import zio.test.Assertion._
import zio.test.environment._

object OptionsSpec extends DefaultRunnableSpec {
  def spec = suite("OptionsSpec")(
    test("horse has a rider") {
      for {
        horseWithRider <- ZIO.attempt(Options.horseWithRider)
      } yield assert(horseWithRider.rider)(isSome)
    }
  )
}