package dysangelist.demo.model.error

import org.http4s.Status

object ErrorApi {
  trait ApiError extends Throwable {
    val responseType: Status
    val m: String
  }

  case class NotFound(m: String) extends ApiError {
    override val responseType: Status = Status.NotFound
  }
  case class BadRequest(m: String) extends ApiError {
    override val responseType: Status = Status.BadRequest
  }
  case class PreconditionFailed(m: String) extends ApiError {
    override val responseType: Status = Status.PreconditionFailed
  }
  case class InternalServerError(m: String) extends ApiError {
    override val responseType: Status = Status.InternalServerError
  }

  case class Unauthorized(m: String) extends ApiError {
    override val responseType: Status = Status.Unauthorized
  }
}
