name := "scalapy-gym-facade"

lazy val scala211 = "2.11.1"
lazy val scala212 = "2.12.1"
lazy val scala213 = "2.13.1"
lazy val supportedScalaVersion = Seq(scala212, scala213)
version := "0.1"

ThisBuild / organization       := "io.github.cric96"
ThisBuild / crossScalaVersions := supportedScalaVersion

idePackagePrefix := Some("io.github.cric96")

libraryDependencies += "me.shadaj" %% "scalapy-core" % "0.5.0"
libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")
fork           := true

import scala.sys.process._

lazy val pythonLdFlags = {
  val withoutEmbed = "python3-config --ldflags".!!
  if (withoutEmbed.contains("-lpython")) {
    withoutEmbed.split(' ').map(_.trim).filter(_.nonEmpty).toSeq
  } else {
    val withEmbed = "python3-config --ldflags --embed".!!
    withEmbed.split(' ').map(_.trim).filter(_.nonEmpty).toSeq
  }
}

lazy val pythonLibsDir =
  pythonLdFlags.find(_.startsWith("-L")).get.drop("-L".length)

javaOptions += s"-Djna.library.path=$pythonLibsDir"
