package dysangelist.demo.model

import java.time.LocalDateTime

case class Id[A <: Entity[A]](id: Int) extends AnyVal

trait Entity[A <: Entity[A]] {
  def id: Id[A]
  def createdAt: LocalDateTime
}
