
lazy val `lifecycle-dependent-typed-object` = (project in file("."))
  .settings(
    organization := "com.ikempf",
    name := "lifecycle-dependent-typed-object",
    scalaVersion := "2.13.3",
    libraryDependencies ++= List(
      "org.typelevel" %% "cats-core" % "2.1.0"
    ),
    addCompilerPlugin(("org.typelevel" %% "kind-projector" % "0.11.0").cross(CrossVersion.full)),
    scalacOptions ++= List(
      "-target:jvm-1.8",
      "-feature",
      "-encoding",
      "UTF-8",
      "-unchecked",
      "-deprecation",
      "-language:higherKinds",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-unused:implicits",
      "-Ywarn-unused:imports",
      "-Ywarn-unused:locals",
      "-Ywarn-unused:params",
      "-Ywarn-unused:patvars",
      "-Ywarn-unused:privates",
      "-Ywarn-value-discard"
    ),
  )
