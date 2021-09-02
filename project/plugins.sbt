addSbtPlugin("org.jetbrains" % "sbt-ide-settings" % "1.1.0")
// Linter
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.16")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.3")
// Publish helper
addSbtPlugin("com.dwijnand" % "sbt-dynver" % "4.1.1")
// Site generator
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.4.1")
// Publish pages
addSbtPlugin("io.kevinlee" % "sbt-github-pages" % "0.6.0")
// Coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.8.2")
// Sonatype upload
addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.5.7")