name := """backend"""
organization := "com.pk"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies ++= Seq(
  "com.pauldijou" %% "jwt-play" % "4.2.0",
  "com.pauldijou" %% "jwt-core" % "4.2.0",
  "com.auth0" % "jwks-rsa" % "0.6.1"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.pk.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.pk.binders._"
