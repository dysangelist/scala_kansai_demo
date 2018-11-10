package dysangelist.demo.service

import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

class HealthcheckService[F[_]](implicit F: Effect[F]) extends Http4sDsl[F] {
  val hc: HttpService[F] = HttpService[F] {
    case GET -> Root / "status" => Ok()
  }
}
