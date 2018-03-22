package com.example.djkhaledsux.stuff

import cats.effect.IO
import fs2.StreamApp
import io.circe._
import io.circe.generic.auto._
import cats.syntax.all._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object HelloWorldServer extends StreamApp[IO] with Http4sDsl[IO] {
  implicit val decoder: EntityDecoder[IO, Ayy] = jsonOf[IO, Ayy]

  val service = HttpService[IO] {
    case r @ POST -> Root / "hello" =>
      r.attemptAs[Ayy].value.flatMap(s => IO(println(s.isRight))) >>
        Ok(Json.obj("message" -> Json.fromString(s"Hello")))
  }

  def stream(args: List[String], requestShutdown: IO[Unit]) =
    BlazeBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .mountService(service, "/")
      .serve
}
