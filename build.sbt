import BuildHelper._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"

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
