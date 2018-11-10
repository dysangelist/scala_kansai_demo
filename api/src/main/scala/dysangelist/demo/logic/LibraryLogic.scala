package dysangelist.demo.logic

import java.time.LocalDateTime

import cats.Monad
import cats.implicits._
import dysangelist.demo.DSL
import dysangelist.demo.algebra.algebra.{AggregationAlgebra, BookAlgebra, UserAlgebra}
import dysangelist.demo.model._
import dysangelist.demo.model.error.ErrorApi.ApiError
import dysangelist.demo.model.error.domain.DomainErrorApi._

import scala.language.higherKinds

class LibraryLogic[F[_]: Monad](
    users: UserAlgebra[F],
    books: BookAlgebra[F],
    aggregator: AggregationAlgebra[F])
    extends DSL[F] {

  def index(): F[List[Book]] = books.findAll()

  import dysangelist.demo.model.UserStatus._

  def borrowBook(userId: Id[User], bookId: Id[Book]): F[Either[ApiError, Book]] = {
    for {
      user <- aggregator.findUser(userId)
      book <- userStatus(user.borrowedBooks) match {
        case TooManyBooks => error(TooManyBooksCheckedOutError)
        case BookOverdue => error(BookOverDueError)
        case _ => reserveBook(bookId, userId)
      }
    } yield book
  }

  def reserveBook(bookId: Id[Book], userId: Id[User]): F[Either[ApiError, Book]] = {
    import dysangelist.demo.model.BookStatus._
    for {
      book <- books.find(bookId)
      reservedBook <- book.status match {
        case Available => result(aggregator.borrowBook(userId, bookId))
        case _ => error(BookUnavailableError)
      }
    } yield reservedBook
  }

  private def userStatus(borrowedBooks: List[Book]): UserStatus = {
    borrowedBooks match {
      case l if l.length >= BorrowLimit => UserStatus.TooManyBooks
      case l if l.exists(a => a.dueDate.isBefore(LocalDateTime.now())) =>
        UserStatus.BookOverdue
      case _ => UserStatus.CanBorrow
    }
  }
  def returnBook(userId: Id[User], bookId: Id[Book]): F[Either[ApiError, Book]] = {
    result(aggregator.returnBook(userId, bookId))
  }
}
