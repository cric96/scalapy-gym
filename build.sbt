name := "scalapy-gym"

lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.12"
lazy val scala213 = "2.13.6"
lazy val supportedScalaVersion = Seq(scala212, scala213)

ThisBuild / scalaVersion       := scala213
ThisBuild / organization       := "io.github.cric96"
ThisBuild / crossScalaVersions := supportedScalaVersion
scmInfo := Some(
  ScmInfo(url("https://github.com/cric96/scalapy-gym"), "scm:git:git@github.com:cric96/scalapy-gym.git")
)
ThisBuild / wartremoverErrors ++= Warts.all
ThisBuild / idePackagePrefix   := Some("io.github.cric96")

libraryDependencies += "me.shadaj" %% "scalapy-core" % "0.5.0"
libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")
fork           := true

// Python integration

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

// Site generation
enablePlugins(SiteScaladocPlugin)

// Gh pages publish
enablePlugins(GhpagesPlugin)
git.remoteRepo := "git@github.com:{your username}/{your project}.git"
git.remoteRepo := scmInfo.value.get.connection.replace("scm:git:", "")
