package dysangelist.demo.model

sealed abstract class BookStatus(val status: String)

object BookStatus {
  case object Borrowed extends BookStatus("Borrowed")
  case object Available extends BookStatus("Available")
}

sealed trait UserStatus

object UserStatus {
  case object BookOverdue extends UserStatus
  case object TooManyBooks extends UserStatus
  case object CanBorrow extends UserStatus
}
