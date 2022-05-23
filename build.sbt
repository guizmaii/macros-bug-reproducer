import BuildHelper._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / version                    := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion               := "2.13.8"
ThisBuild / scalafmtCheck              := true
ThisBuild / scalafmtSbtCheck           := true
ThisBuild / scalafmtOnCompile          := (if (insideCI.value) false else true)
ThisBuild / semanticdbEnabled          := true
ThisBuild / semanticdbOptions += "-P:semanticdb:synthetics:on"
ThisBuild / semanticdbVersion          := scalafixSemanticdb.revision // use Scalafix compatible version
ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value)
ThisBuild / scalafixDependencies ++= List(
  "com.github.vovapolu"                      %% "scaluzzi" % "0.1.21",
  "io.github.ghostbuster91.scalafix-unified" %% "unified"  % "0.0.8",
)

addCommandAlias("check", "; scalafmtSbtCheck; scalafmtCheckAll; compile:scalafix --check; test:scalafix --check")

addCommandAlias(
  "reproduce",
  "reload;clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;" +
    "clean;compile;",
)

lazy val root =
  (project in file("."))
    .disablePlugins(RevolverPlugin)
    .settings(name := "macros-bug-reproducer")
    .settings(noDoc: _*)
    .settings(noPublishSettings: _*)
    .aggregate(core, `core-macros`)

lazy val core =
  project
    .in(file("core"))
    .settings(commonAppModuleSettings: _*)
    .settings(Revolver.enableDebugging())
    .dependsOn(`core-macros`)

lazy val `core-macros` =
  project
    .in(file("core-macros"))
    .disablePlugins(RevolverPlugin)
    .settings(commonAppModuleSettings: _*)
    .settings(libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value)
