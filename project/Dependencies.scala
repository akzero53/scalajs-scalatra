import sbt._

/**
  * Library dependencies
  */
object Dependencies {
  val scalatraVersion = "2.4.1"

  val scalatra = "org.scalatra" %% "scalatra" % scalatraVersion
  val javaxServletApi = "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
}