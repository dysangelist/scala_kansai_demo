package dysangelist.demo.algebra.algebra

import cats.Monad
import cats.data.Kleisli
import dysangelist.demo.model._
import cats.implicits._

import scala.language.higherKinds

class AggregationAlgebra[F[_]: Monad](u: UserAlgebra[F], b: BookAlgebra[F]) {
  def findUser: Kleisli[F, Id[User], UserAggregate] = Kleisli { id =>
    for {
      user          <- u.find(id)
      bookIds       <- u.userBorrowedBooks(id)
      borrowedBooks <- bookIds.map(b.find.apply).sequence
    } yield UserAggregate(user, borrowedBooks)
  }

  def borrowBook: Kleisli[F, (Id[User], Id[Book]), Book] = Kleisli {
    case (userId, bookId) =>
      for {
        a <- u.addToUserBorrowedList(userId, bookId)
        b <- b.changeStatus(bookId, BookStatus.Borrowed)
      } yield b
  }

  def returnBook: Kleisli[F, (Id[User], Id[Book]), Book] = Kleisli {
    case (userId, bookId) =>
      for {
        a <- u.removeFromUserBorrowedList(userId, bookId)
        b <- b.changeStatus(bookId, BookStatus.Available)
      } yield b
  }
}
