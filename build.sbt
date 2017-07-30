name := """sample"""
organization := "org.example"
version := "0.1-SNAPSHOT"
scalaVersion := "2.10.6"

sbtPlugin := true

ScriptedPlugin.scriptedSettings
scriptedLaunchOpts ++= Seq(
  "-Xmx1024M",
  "-Dplugin.version=" + version.value
)
