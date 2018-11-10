import sbt._

object Library {
  private val ver = new {
    val http4s = "0.18.4"
    val circe = "0.9.3"
  }

  private lazy val http4sBlaze = "org.http4s"       %% "http4s-blaze-server" % ver.http4s
  private lazy val http4sCirce = "org.http4s"       %% "http4s-circe"        % ver.http4s
  private lazy val http4sDsl = "org.http4s"         %% "http4s-dsl"          % ver.http4s
  private lazy val http4sBlazeClient = "org.http4s" %% "http4s-blaze-client" % ver.http4s

  private lazy val circeGeneric = "io.circe"    %% "circe-generic"    % ver.circe
  private lazy val circeLiteral = "io.circe"    %% "circe-literal"    % ver.circe
  private lazy val circeDerivation = "io.circe" %% "circe-derivation" % "0.9.0-M4"
  private lazy val circeParser = "io.circe"     %% "circe-parser"     % ver.circe
  private lazy val circeJava8 = "io.circe"      %% "circe-java8"      % ver.circe

  private lazy val circeExtras = "io.circe" %% "circe-generic-extras" % ver.circe

  lazy val logback = "ch.qos.logback"                  % "logback-classic" % "1.2.3"
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging"  % "3.8.0"

  lazy val scalaCheck = "org.scalacheck"               %% "scalacheck"     % "1.13.5" % Test
  lazy val scalaMock = "org.scalamock"                 %% "scalamock"      % "4.1.0" % Test
  lazy val scalaTest = "org.scalatest"                 %% "scalatest"      % "3.0.5" % Test

  private lazy val circeCore = "io.circe" %% "circe-core" % ver.circe

  lazy val testSuite = Seq(scalaCheck, scalaMock, scalaTest)

  lazy val http4s = Seq(http4sBlaze, http4sCirce, http4sDsl, http4sBlazeClient)
  lazy val circe = Seq(circeGeneric, circeLiteral, circeDerivation, circeExtras, circeParser, circeJava8)
  lazy val loggingSuite = Seq(logback, scalaLogging)
}
