import com.typesafe.sbt.packager.Keys.{dockerBaseImage => _, dockerExposedPorts => _, dockerRepository => _, dockerUpdateLatest => _}
import sbt.Keys._
import sbt.{Compile, _}

object BuildHelper {

  def env(v: String): Option[String] = sys.env.get(v)

  lazy val commonAppModuleSettings = Seq(
    libraryDependencies += compilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    javacOptions ++= Seq("-source", "17", "-target", "17"),
    scalacOptions ++= Seq("-Ymacro-annotations", "-Xsource:3", "-target:17", "-Xlog-free-terms"),
    // https://www.scala-lang.org/api/2.13.5/scala/annotation/elidable.html
    scalacOptions ++= (if (insideCI.value) Seq("-Xelide-below", scala.annotation.elidable.INFO.toString) else Nil),
    scalacOptions --= (if (insideCI.value) Nil else Seq("-Xfatal-warnings")), // enforced by the pre-push hook too
    Test / scalacOptions ++= Seq("-Wmacros:after"),                           // https://diffx-scala.readthedocs.io/en/latest/#tips-and-tricks
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    (Test / parallelExecution) := false,
    (Test / fork)              := true,
  ) ++ noDoc

  lazy val noDoc = Seq(
    (Compile / doc / sources)                := Seq.empty,
    (Compile / packageDoc / publishArtifact) := false,
  )

  /**
   * Copied from Cats
   */
  lazy val noPublishSettings = Seq(
    publish         := {},
    publishLocal    := {},
    publishM2       := {},
    publishArtifact := false,
  )

}
