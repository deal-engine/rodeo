package rodeo

import zio.test._
import zio.test.TestAspect.ignore

object TuplesSpec extends ZIOSpecDefault {

  import rodeo.Tuples._

  def spec = suite("TupleSpec")(
    test("nameAndAge have been assigned to nonEmpty string and positive age") {
      assertTrue(nameAndAge._1.nonEmpty)
      assertTrue(nameAndAge._2 > 0)
    } @@ ignore,

    test("secondFriend is fabian") {
      assertTrue(secondFriend == "fabian")
    } @@ ignore
  )
}
