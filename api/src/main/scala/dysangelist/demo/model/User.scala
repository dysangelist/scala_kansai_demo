package dysangelist.demo.model

import java.time.LocalDateTime

case class User(id: Id[User], name: String, createdAt: LocalDateTime) extends Entity[User]
case class UserAggregate(user: User, borrowedBooks: List[Book])
