name := "mhtml-visualize"
version := "0.1"
scalaVersion := "2.12.6"

enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "in.nvilla"    %%% "monadic-html"  % "0.4.0-RC1",
  "org.scala-js" %%% "scalajs-dom"   % "0.9.2",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

jsDependencies += "org.webjars.bower" % "cytoscape" % "3.2.5" / "3.2.5/dist/cytoscape.js"

scalaJSUseMainModuleInitializer := true
