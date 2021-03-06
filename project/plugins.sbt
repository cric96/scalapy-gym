addSbtPlugin("org.jetbrains" % "sbt-ide-settings" % "1.1.0")
// Linter
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.20")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.4.6")
// Publish helper
addSbtPlugin("com.dwijnand" % "sbt-dynver" % "4.1.1")
// Site generator
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.4.1")
// Publish pages
addSbtPlugin("io.kevinlee" % "sbt-github-pages" % "0.10.0")
// Coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.0")
// Sonatype upload
addSbtPlugin("com.github.sbt" % "sbt-ci-release"       % "1.5.10")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype"         % "3.9.13")
addSbtPlugin("io.shiftleft"   % "sbt-ci-release-early" % "2.0.32") //for auto-tagging
