package org.example.sbt

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import java.util.concurrent.atomic.AtomicBoolean

object SamplePlugin extends AutoPlugin {
  override def trigger = allRequirements
  override def requires = JvmPlugin

  private val processed = new AtomicBoolean(false)

  object autoImport {
    val myTask = taskKey[Seq[File]]("task files")

    def myPluginSettings(cond: Boolean) = {
      processed.set(true)
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

  override def projectSettings = {
    if (!processed.get) {
      myPluginSettings(cond = true)
    }
    else Seq.empty
  }
}
