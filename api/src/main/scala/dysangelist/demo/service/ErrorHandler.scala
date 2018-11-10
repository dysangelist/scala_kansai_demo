package dysangelist.demo.service

import cats.effect.Effect
import com.typesafe.scalalogging.LazyLogging
import dysangelist.demo.model.error.ErrorApi.ApiError
import org.http4s.Response
import org.http4s.dsl.Http4sDsl

class ErrorHandler[F[_]](implicit F: Effect[F]) extends Http4sDsl[F] with LazyLogging {
  def errorHandler: PartialFunction[Throwable, Response[F]] = {
    case e: ApiError => Response[F](status = e.responseType)
    case _ => Response[F](status = InternalServerError)
  }
}
