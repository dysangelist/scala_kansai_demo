package dysangelist.demo.model

import java.time.LocalDateTime

case class Book(
    id: Id[Book],
    title: String,
    status: BookStatus,
    dueDate: LocalDateTime,
    createdAt: LocalDateTime)
    extends Entity[Book]
