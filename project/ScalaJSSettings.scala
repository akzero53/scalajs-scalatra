import sbt._
import sbt.Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object ScalaJSSettings {
  private val generateIndexHtml = taskKey[File]("generate index.html")

  /**
    * Create settings for ScalaJS web application client project
    * @param aPageTitle     The title of the page.
    * @param aServerProject Directory to output these:
    *                         - index.html
    *                         - js (compiled javascript files)
    * @return               settings.
    */
  def createScalaJsDefaultSettings(aPageTitle: String, aServerProject: Project) = Seq(fastOptJS, fullOptJS, packageMinifiedJSDependencies, packageJSDependencies, packageScalaJSLauncher).map { packageJSKey =>
      crossTarget in (Compile, packageJSKey) := (sourceDirectory in aServerProject).value / "main/webapp/js"
    } ++ Seq(
      persistLauncher := true,
      artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value / ((moduleName in fastOptJS).value + "-opt.js")),
      artifactPath in (Compile, packageMinifiedJSDependencies) := ((crossTarget in (Compile, packageMinifiedJSDependencies)).value / ((moduleName in packageMinifiedJSDependencies).value + "-jsdeps.js")),
      generateIndexHtml := {
        val tFile = (sourceDirectory in aServerProject).value / "main/webapp/index.html"
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
}