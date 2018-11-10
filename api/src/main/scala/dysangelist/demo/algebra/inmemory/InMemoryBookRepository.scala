package dysangelist.demo.algebra.inmemory

import java.time.LocalDateTime

import cats.data.Kleisli
import cats.effect.Effect
import dysangelist.demo.model.error.repo.Error.EntityNotFoundException
import dysangelist.demo.model.{Book, BookStatus, Id, User}
import dysangelist.demo.algebra.algebra.BookAlgebra
import cats.implicits._

import scala.collection.mutable
import scala.language.higherKinds

class InMemoryBookRepository[F[_]](implicit F: Effect[F]) extends BookAlgebra[F] {
  val books = mutable.HashMap(
    Id[Book](1) -> Book(Id[Book](1),
                        "",
                        BookStatus.Available,
                        LocalDateTime.now().plusDays(10),
                        LocalDateTime.now()),
    Id[Book](2) -> Book(Id[Book](2),
                        "Book",
                        BookStatus.Available,
                        LocalDateTime.now().plusDays(10),
                        LocalDateTime.now())
  )

  override def find: Kleisli[F, Id[Book], Book] =
    Kleisli(id => F.fromOption(books.get(id), new EntityNotFoundException("book")))

  override def findAll: F[List[Book]] = F.delay(books.values.toList)

  override def changeStatus: Kleisli[F, (Id[Book], BookStatus), Book] =
    Kleisli {
      case (bookId: Id[Book], status: BookStatus) =>
        for {
          bookOpt <- F.delay(books.get(bookId))
          book    <- F.fromOption(bookOpt, new EntityNotFoundException("book"))
          updatedBook = book.copy(status = status)
          _ <- F.delay(books.update(bookId, updatedBook))
        } yield updatedBook
    }
}
