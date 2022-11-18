import mill._, scalalib._

object rodeo extends ScalaModule{
  override def scalaVersion = "2.13.8"
  override def ivyDeps = Agg(ivy"dev.zio::zio-test-sbt:2.0.2")

  object test extends Tests {
    override def testFramework = "zio.test.sbt.ZTestFramework"
  }
}

object parserCombinators extends ScalaModule{
  override def scalaVersion = "3.2.1"
  override def ammoniteVersion = "2.5.5-17-df243e14"
  override def ivyDeps = Agg(ivy"dev.zio::zio:2.0.3",
    ivy"org.typelevel::cats-core:2.9.0",
    ivy"org.typelevel::mouse:1.2.1",
    ivy"dev.zio::zio-interop-cats:22.0.0.0"
  )
}
