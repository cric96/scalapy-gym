import scala.sys.process._
name := "scalapy-gym"
// Plugins
enablePlugins(GitHubPagesPlugin)
enablePlugins(SiteScaladocPlugin)
enablePlugins(GitVersioning)
//enablePlugins(HugoPlugin)
// For publish setting
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
lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.15"
lazy val scala213 = "2.13.6"
lazy val supportedScalaVersion = Seq(scala212, scala213)

ThisBuild / scalaVersion       := scala213
ThisBuild / crossScalaVersions := supportedScalaVersion
ThisBuild / wartremoverErrors ++= Warts.all
ThisBuild / idePackagePrefix   := Some("io.github.cric96")

libraryDependencies += "me.shadaj" %% "scalapy-core" % "0.5.0"
libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test"

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

javaOptions += s"-Djna.library.path=$pythonLibsDir"
// Todo to fix
// Site generation
// Hugo / sourceDirectory := sourceDirectory.value / "doc"
// baseURL in Hugo := uri("https://cric96.github.io/scalapy-gym")
// Gh pages publish
gitHubPagesSiteDir := baseDirectory.value / "target/site"
