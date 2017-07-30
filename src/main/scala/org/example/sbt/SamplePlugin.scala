package org.example.sbt

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object SamplePlugin extends AutoPlugin {
  override def trigger = allRequirements
  override def requires = JvmPlugin

  object autoImport {
    val myTask = taskKey[Seq[File]]("task files")

    def myPluginSettings(cond: Boolean) = {
      println(s"condition: $cond")

      inConfig(Compile)(Seq(myTask := Seq.empty)) ++ (
        if(!cond) Seq.empty
        else {
          // shouldn't get here
          val input = compileInputs in (Compile, compile)
          input := (input dependsOn (myTask in Compile)).value
        }
      )
    }
  }
  import autoImport._

  override def projectSettings = myPluginSettings(cond = true)
}
