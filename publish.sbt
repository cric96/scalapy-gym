import xerial.sbt.Sonatype._

ThisBuild / publishMavenStyle      := true
ThisBuild / dynverVTagPrefix       := false
ThisBuild / publishTo              := sonatypePublishToBundle.value
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"

sonatypeProjectHosting := Some(GitHubHosting("cric96", "scalapy-gym", "gianluca.aguzzi@studio.unibo.it"))
