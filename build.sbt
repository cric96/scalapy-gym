name := "scalapy-gym-facade"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("org.cric96.github.io")

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
