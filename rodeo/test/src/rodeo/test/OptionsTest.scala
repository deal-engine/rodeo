package rodeo.test

import zio._
import zio.test._

object OptionsSpec extends ZIOSpecDefault {
  def spec = suite("OptionsSpec")(
    test("math works") {
      assertTrue(2 + 2 == 4)
    }
  )
}