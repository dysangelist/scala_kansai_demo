package dysangelist.demo.algebra

import cats.Monad
import cats.effect.Effect
import dysangelist.demo.algebra.algebra.{AggregationAlgebra, BookAlgebra, UserAlgebra}
import dysangelist.demo.algebra.inmemory.{InMemoryBookRepository, InMemoryUserRepository}

object Algebra {
  def InMemoryEffectRepresentation[F[_]: Effect]: Algebra[F] =
    Algebra[F](new InMemoryUserRepository[F](), new InMemoryBookRepository[F]())
}

case class Algebra[F[_]: Monad](users: UserAlgebra[F], books: BookAlgebra[F]) {
  val aggregator = new AggregationAlgebra(users, books)
}
