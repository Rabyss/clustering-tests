name := "clusters"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.6.1",
  "com.github.nscala-time" %% "nscala-time" % "2.14.0",
  "org.scalanlp" %% "breeze" % "0.12",
  "org.scalanlp" %% "breeze-natives" % "0.12",
  "org.scalanlp" %% "breeze-viz" % "0.12"
)
