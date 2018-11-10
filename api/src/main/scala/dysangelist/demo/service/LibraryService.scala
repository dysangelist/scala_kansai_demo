package dysangelist.demo.service

import cats.effect.Effect
import cats.implicits._
import dysangelist.demo.logic.LibraryLogic
import dysangelist.demo.model.error.ErrorApi.ApiError
import dysangelist.demo.model.{Book, Id, User}
import io.circe.generic.auto._
import org.http4s.circe.jsonEncoderOf
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityEncoder, HttpService}

import scala.language.higherKinds

import dysangelist.demo.support.JsonSupport._
import org.http4s.circe._

class LibraryService[F[_]](l: LibraryLogic[F], errorHandler: ErrorHandler[F])(
    implicit F: Effect[F])
    extends Http4sDsl[F] {
  implicit val bookEncoder: EntityEncoder[F, Book] = jsonEncoderOf[F, Book]
  implicit val bookListEncoder: EntityEncoder[F, List[Book]] =
    jsonEncoderOf[F, List[Book]]
  implicit val userEncoder: EntityEncoder[F, User] = jsonEncoderOf[F, User]

  val s: HttpService[F] = HttpService[F] {
    case req @ GET -> Root / "index" =>
      for {
        b <- l.index()
        r <- Ok(b)
      } yield r

    case req @ POST -> Root / "borrow" / IntVar(userId) / IntVar(bookId) =>
      for {
        result <- toResult(l.borrowBook(Id[User](userId), Id[Book](bookId)))
        r      <- Ok(result)
      } yield r

    case req @ POST -> Root / "return" / IntVar(userId) / IntVar(bookId) =>
      for {
        result <- toResult(l.returnBook(Id[User](userId), Id[Book](bookId)))
        r      <- Ok(result)
      } yield r

  }.handleError(errorHandler.errorHandler)

  def toResult[A](r: F[Either[ApiError, A]]): F[A] =
    for {
      either <- r
      resp   <- F.fromEither(either)
    } yield resp
}
