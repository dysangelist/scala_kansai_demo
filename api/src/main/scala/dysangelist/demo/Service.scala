package dysangelist.demo

import cats.effect.Effect
import dysangelist.demo.service.{ErrorHandler, HealthcheckService, LibraryService}
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpService
import cats.implicits._
import dysangelist.demo.algebra.Algebra
import dysangelist.demo.logic.LibraryLogic

import scala.language.higherKinds

class Service[F[_]: Effect]() extends Http4sDsl[F] {
  private val representation = Algebra.InMemoryEffectRepresentation[F]
  private val logic =
    new LibraryLogic(representation.users,
                     representation.books,
                     representation.aggregator)
  private val common = new HealthcheckService[F]()
  private val errorHandler = new ErrorHandler[F]()
  private val library = new LibraryService(logic, errorHandler)

  val service: HttpService[F] = library.s <+> common.hc
}
