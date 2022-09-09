package rodeo

import zio.test.TestAspect._
import zio.test._

object RodeoSpec extends ZIOSpecDefault {

  override def spec: Spec[Any, Any] =
    suite("Scala Basics")(
      suite("Variables")(Variables.exercises),
      suite("Tuples")(Tuples.exercises),
      suite("Options")(Options.exercises)
    ) @@ sequential

}
