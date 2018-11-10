import sbt.ModuleID

object Dependencies {
  import Library._

  val api: Seq[ModuleID] = http4s ++ circe ++
    loggingSuite ++ testSuite
}
