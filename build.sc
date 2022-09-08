import mill._, scalalib._

object rodeo extends ScalaModule{
  override def scalaVersion = "2.13.8"

  object test extends Tests {
    override def ivyDeps = Agg(ivy"dev.zio::zio-test-sbt:2.0.2")
    override def testFramework = "zio.test.sbt.ZTestFramework"
  }
}
