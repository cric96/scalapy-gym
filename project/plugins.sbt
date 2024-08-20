addSbtPlugin("org.jetbrains" % "sbt-ide-settings" % "1.1.0")
// Linter
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.21")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.5.2")
// Publish helper
addSbtPlugin("com.github.sbt" % "sbt-dynver" % "5.0.1")
// Site generator
addSbtPlugin("com.github.sbt" % "sbt-site" % "1.6.0")
// Publish pages
addSbtPlugin("io.kevinlee" % "sbt-github-pages" % "0.14.0")
// Coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.0")
// Sonatype upload
addSbtPlugin("com.github.sbt" % "sbt-ci-release"       % "1.6.0")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype"         % "3.11.3")
addSbtPlugin("io.shiftleft"   % "sbt-ci-release-early" % "2.0.46") //for auto-tagging
