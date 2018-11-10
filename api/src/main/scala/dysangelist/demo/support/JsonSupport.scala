package dysangelist.demo.support

import java.time.LocalDateTime

import io.circe.java8.time._
import dysangelist.demo.model.{BookStatus, Entity, Id}
import io.circe.{Decoder, Encoder}

object JsonSupport {
  implicit def idDecoder[A <: Entity[A]]: Decoder[Id[A]] = Decoder.decodeInt.map(Id[A])

  implicit def idEncoder[A <: Entity[A]]: Encoder[Id[A]] = Encoder[Int].contramap(_.id)
  implicit def bookStatusEncoder: Encoder[BookStatus] =
    Encoder[String].contramap(_.status)

  implicit final val encodeLocalDateDefault: Encoder[LocalDateTime] =
    encodeLocalDateTimeDefault
}
