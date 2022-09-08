package rodeo

import zio.test._
import zio.test.TestAspect.ignore

object OptionsSpec extends ZIOSpecDefault {
  import Options._
  def spec = suite("OptionsSpec")(
    test("the answer to the universe exists") {
      assertTrue(theAnwserToTheUniverseAndEverything.contains(42))
    } @@ ignore,

    test("matching on some") {
      assertTrue(youKnow == "you know it all")
    } @@ ignore,

    test("getOrElse") {
      assertTrue(fury(None) == "its my first rodeo") &&
        assertTrue(fury(Some("my second rodeo")) == "my second rodeo")
    } @@ ignore,

  )
}
