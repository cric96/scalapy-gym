import scala.sys.process._

name := "scalapy-gym"
// Plugins
enablePlugins(GitHubPagesPlugin)
enablePlugins(SiteScaladocPlugin)
enablePlugins(GitVersioning)
inThisBuild(
  List(
    organization           := "io.github.cric96",
    homepage               := Some(url("https://github.com/cric96/scalapy-gym")),
    licenses               := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    sonatypeCredentialHost := Sonatype.sonatype01,
    sonatypeRepository     := s"https://${Sonatype.sonatype01}/service/local",
    developers := List(
      Developer(
        "cric96",
        "Gianluca Aguzzi",
        "gianluca.aguzzi@studio.unibo.it",
        url("https://cric96.github.io/")
      )
    )
  )
)
// Common configuration
ThisBuild / scalaVersion       := scala213
ThisBuild / crossScalaVersions := supportedScalaVersion
ThisBuild / wartremoverErrors ++= Warts.all
ThisBuild / idePackagePrefix   := Some("io.github.cric96")

lazy val scala211 = "2.11.12"

lazy val scala212 = "2.12.20"

lazy val scala213 = "2.13.6"

lazy val supportedScalaVersion = Seq(scala212, scala213)

libraryDependencies += "me.shadaj"   %% "scalapy-core" % "0.5.2"
libraryDependencies += "com.lihaoyi" %% "utest"        % "0.8.4" % "test"
libraryDependencies += "com.outr"    %% "scribe"       % "3.15.2"

testFrameworks += new TestFramework("utest.runner.Framework")
fork           := true

// Python integration
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

javaOptions        += s"-Djna.library.path=$pythonLibsDir"
gitHubPagesSiteDir := baseDirectory.value / "target/site"
