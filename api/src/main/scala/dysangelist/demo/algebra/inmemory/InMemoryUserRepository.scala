package dysangelist.demo.algebra.inmemory

import java.time.LocalDateTime

import cats.data.Kleisli
import cats.effect.Effect
import dysangelist.demo.model._
import dysangelist.demo.algebra.algebra.UserAlgebra
import cats.implicits._
import dysangelist.demo.model.error.repo.Error.EntityNotFoundException

import scala.collection.mutable
import scala.language.higherKinds

class InMemoryUserRepository[F[_]](implicit F: Effect[F]) extends UserAlgebra[F] {
  val users = Map(
    Id[User](1) -> User(Id[User](1), "Dasha", LocalDateTime.now())
  )

  val userBorrowedList = mutable.HashMap.empty[Id[User], List[Id[Book]]]

  override def find =
    Kleisli(id => F.fromOption(users.get(id), new EntityNotFoundException("user")))

  override def userBorrowedBooks: Kleisli[F, Id[User], List[Id[Book]]] = Kleisli { id =>
    F.delay {
      userBorrowedList.get(id).fold[List[Id[Book]]](Nil)(l => l)
    }
  }

  override def addToUserBorrowedList: Kleisli[F, (Id[User], Id[Book]), List[Id[Book]]] =
    Kleisli {
      case (id, book) =>
        F.delay {
          val l = userBorrowedList.get(id).fold[List[Id[Book]]](Nil)(l => l) :+ book
          userBorrowedList.update(id, l)
          l
        }
    }

  override def removeFromUserBorrowedList
    : Kleisli[F, (Id[User], Id[Book]), List[Id[Book]]] = Kleisli {
    case (id, bookId) =>
      F.delay {
        val l = userBorrowedList
          .get(id)
          .fold[List[Id[Book]]](Nil)(l => l)
          .filterNot(_ != bookId)
        userBorrowedList.update(id, l)
        l
      }
  }
}
