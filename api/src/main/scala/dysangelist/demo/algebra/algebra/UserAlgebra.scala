package dysangelist.demo.algebra.algebra

import cats.Monad
import cats.data.Kleisli
import dysangelist.demo.model.{Book, Id, User}

import scala.language.higherKinds

abstract class UserAlgebra[F[_]: Monad] {
  def find: Kleisli[F, Id[User], User]
  def userBorrowedBooks: Kleisli[F, Id[User], List[Id[Book]]]
  def addToUserBorrowedList: Kleisli[F, (Id[User], Id[Book]), List[Id[Book]]]
  def removeFromUserBorrowedList: Kleisli[F, (Id[User], Id[Book]), List[Id[Book]]]
}
