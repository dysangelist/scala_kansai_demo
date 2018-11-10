package dysangelist.demo.algebra.algebra

import cats.Monad
import cats.data.Kleisli
import dysangelist.demo.model.{Book, BookStatus, Id}

import scala.language.higherKinds

abstract class BookAlgebra[F[_]: Monad] {
  def findAll(): F[List[Book]]
  def find: Kleisli[F, Id[Book], Book]
  def changeStatus: Kleisli[F, (Id[Book], BookStatus), Book]
}
