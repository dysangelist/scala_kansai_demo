package dysangelist.demo

import cats.Monad
import dysangelist.demo.model.error.ErrorApi.ApiError
import cats.implicits._

abstract class DSL[F[_]: Monad] {
  def error[A](apiError: ApiError): F[Either[ApiError, A]] =
    apiError.pure[F].map(a => Left(a))
  def result[A](r: F[A]): F[Either[ApiError, A]] = r.map(Right.apply)
}
