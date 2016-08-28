addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.11")

addSbtPlugin("org.scalatra.sbt" % "scalatra-sbt" % "0.5.1")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

lazy val root = (project in file(".")).dependsOn(sbtMultipleProjectPlugin)

lazy val sbtMultipleProjectPlugin = uri("git://github.com/akzero53/sbt-multiple-project-plugin.git#7d77b953a7825904706990d6eda68f3808428ac2")