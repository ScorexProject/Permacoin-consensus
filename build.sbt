organization := "org.consensusresearch"

name := "scorex-perma"

version := "1.2.0"

scalaVersion := "2.11.7"

resolvers += "SonaType" at "https://oss.sonatype.org/content/groups/public"

libraryDependencies ++= Seq(
  "org.consensusresearch" %% "scorex" % "1.2.0",
  "org.scalatest" %% "scalatest" % "2.+" % "test",
  "org.scalactic" %% "scalactic" % "2.+" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.+" % "test"
)

