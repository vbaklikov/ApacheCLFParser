name := "ApacheCLFParser"

version := "1.0"

scalaVersion := "2.11.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Typesafe Simple Repository" at
  "http://repo.typesafe.com/typesafe/simple/maven-releases/"

scalacOptions += "-deprecation"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"