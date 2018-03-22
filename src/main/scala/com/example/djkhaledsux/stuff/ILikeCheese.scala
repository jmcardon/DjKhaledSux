package com.example.djkhaledsux.stuff

import java.nio.file.FileSystems

import cats.effect.IO
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import fs2._
object ILikeCheese extends StreamApp[IO] {
  def stream(args: List[String],
             requestShutdown: IO[Unit]): fs2.Stream[IO, StreamApp.ExitCode] = {
    val stuff = Lmao("kappa123").asJson.toString()
    val lmao = Ayy(List.fill(1000000)(stuff)).asJson.toString()

    Stream
      .emit(lmao)
      .through(text.utf8Encode)
      .covary[IO]
      .through(io.file.writeAll[IO](FileSystems.getDefault.getPath("./kappa123.json")))
      .map(_ => StreamApp.ExitCode.Success)
  }
}
