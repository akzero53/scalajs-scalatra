package com.github.akzero53

import scala.scalajs.js

@scala.scalajs.js.annotation.JSExport
object Main extends js.JSApp {
  @scala.scalajs.js.annotation.JSExport
  override def main(): Unit = {
    scalajs.js.eval("alert('ScalaJS / Scalatra Web Application Hello World!!');")
  }
}
