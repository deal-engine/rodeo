package rodeo

import zio.test.{ZIOSpecDefault, assertTrue}

object OptionsSpec extends ZIOSpecDefault {
  def spec = suite("OptionsSpec")(
    test("math works") {
      assertTrue(2 + 2 == 4)
    }
  )
}
