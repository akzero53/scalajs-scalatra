import sbt._
import sbt.Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object ScalaJSSettings {
  private val generateIndexHtml = taskKey[File]("generate index.html")

  def createScalaJsCustomSettings(aPageTitle: String, aServerProject: Project, aArtifactsDirectoryResolver: File => File) = Seq(fastOptJS, fullOptJS, packageMinifiedJSDependencies, packageJSDependencies, packageScalaJSLauncher).map { packageJSKey =>
    crossTarget in (Compile, packageJSKey) := aArtifactsDirectoryResolver((sourceDirectory in aServerProject).value) / "js"
  } ++ Seq(
    persistLauncher := true,
    artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value / ((moduleName in fastOptJS).value + "-opt.js")),
    artifactPath in (Compile, packageMinifiedJSDependencies) := ((crossTarget in (Compile, packageMinifiedJSDependencies)).value / ((moduleName in packageMinifiedJSDependencies).value + "-jsdeps.js")),
    generateIndexHtml := {
      val tFile = aArtifactsDirectoryResolver((sourceDirectory in aServerProject).value) / "index.html"
      val tIndexHtml = s"""<!DOCTYPE html>
                           |<html lang="en">
                           |  <head>
                           |    <meta charset="UTF-8">
                           |    <title>$aPageTitle</title>
                           |  </head>
                           |  <body>
                           |    <div id="root"></div>
                           |    <script src="js/client-jsdeps.js"></script>
                           |    <script src="js/client-opt.js"></script>
                           |    <script src="js/client-launcher.js"></script>
                           |  </body>
                           |</html>""".stripMargin
      IO.write(tFile, tIndexHtml)
      tFile
    }
  ) ++ Seq(fastOptJS, fullOptJS).map { packageJSKey =>
    packageJSKey <<= (packageJSKey in Compile).dependsOn(generateIndexHtml)
  }

  def createScalatraScalaJsSettings(aPageTitle: String, aServerProject: Project) = createScalaJsCustomSettings(aPageTitle, aServerProject, _ / "main" / "webapp")
  def createPlayScalaJsSettings(aPageTitle: String, aServerProject: Project) = createScalaJsCustomSettings(aPageTitle, aServerProject, _ / "main" / "public")
}
