organization := "org.consensusresearch"

name := "scorex-perma"

scalaVersion := "2.11.8"

resolvers += "SonaType" at "https://oss.sonatype.org/content/groups/public"
resolvers += "SonaType snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  "com.h2database" % "h2-mvstore" % "1.+",
  "org.consensusresearch" %% "scorex-basics" % version.value,
  "org.scalatest" %% "scalatest" % "2.+" % "test",
  "org.scalactic" %% "scalactic" % "2.+" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.+" % "test"
)

//publishing settings

licenses := Seq("CC0" -> url("https://creativecommons.org/publicdomain/zero/1.0/legalcode"))

homepage := Some(url("https://github.com/ConsensusResearch/Scorex-Lagonaki"))

publishMavenStyle := true

publishArtifact in Test := false

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}


pomIncludeRepository := { _ => false }

licenses in ThisBuild := Seq("CC0" -> url("https://creativecommons.org/publicdomain/zero/1.0/legalcode"))

homepage in ThisBuild := Some(url("https://github.com/ConsensusResearch/Scorex-Lagonaki"))

pomExtra in ThisBuild :=
  <scm>
    <url>git@github.com:ConsensusResearch/Scorex-Lagonaki.git</url>
    <connection>scm:git:git@github.com:ConsensusResearch/Scorex-Lagonaki.git</connection>
  </scm>
    <developers>
      <developer>
        <id>kushti</id>
        <name>Alexander Chepurnoy</name>
        <url>http://chepurnoy.org/</url>
      </developer>
    </developers>


credentials in ThisBuild += Credentials(Path.userHome / ".ivy2" / ".credentials")