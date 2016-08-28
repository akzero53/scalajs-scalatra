import com.github.akzero53._

val commonSettings = CommonSettings(
  organization = "com.github.akzero53",
  version = "0.1.0-SNAPSHOT",
  scalaVersion = "2.11.8"
)
val scalatraVersion = "2.4.1"
val projectFactory = ProjectFactory(commonSettings)
val license = ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))

lazy val root = projectFactory.createRootProject("scalajs-scalatra")
  .settings(licenses += license)
  .aggregate(client, server)

lazy val client = projectFactory.createSubProject("client")
  .settings(ScalaJSSettings.createScalatraScalaJsSettings("ScalaJS - Scalatra / Hello World", server))
  .enablePlugins(ScalaJSPlugin)

lazy val server = projectFactory.createSubProject("server")
  .settings(scalatraSettings)
  .settings(libraryDependencies ++= Seq(
    Dependencies.scalatra,
    Dependencies.javaxServletApi
  ))
  .enablePlugins(JettyPlugin)
