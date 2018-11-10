package dysangelist.demo

import java.net.InetSocketAddress

import cats.effect.{Effect, IO}
import fs2.StreamApp
import org.http4s.HttpService
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration
import scala.language.higherKinds

object Server extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  val server = new ServerStream
  def stream(
      args: List[String],
      requestShutdown: IO[Unit]): fs2.Stream[IO, StreamApp.ExitCode] =
    server.stream[IO]
}

class ServerStream {
  def service[F[_]: Effect](): HttpService[F] =
    new Service[F]().service

  def stream[F[_]: Effect](
      implicit ec: ExecutionContext): fs2.Stream[F, StreamApp.ExitCode] =
    for {
      server <- BlazeBuilder[F]
        .bindSocketAddress(new InetSocketAddress(8080))
        .withIdleTimeout(Duration.Inf)
        .mountService(service(), "/")
        .serve
    } yield server
}
