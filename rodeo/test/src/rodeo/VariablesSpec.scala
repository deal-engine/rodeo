package rodeo

import zio.test._
import zio.test.TestAspect._

object VariablesSpec extends ZIOSpecDefault {

  import rodeo.Variables._

  def spec = suite("VariablesSpec")(
    test("yourAge is assigned to a value greater than zero") {
      assertTrue(yourAge > 0)
    } @@ ignore,

    test("seen is assigned to value 99") {
      assertTrue(seen == 99)
    } @@ ignore,

    test("mutableSeq has changed it's internal value even if the val has not changed") {
      assertTrue(rodeo.exists(_ > 0))
    } @@ ignore
  )
}
