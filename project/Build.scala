import sbt._
import Keys._

/**
  *
  * Created by ZJK on 2017/8/4.
  */
object IMBuild extends Build{

  val libVersion = "1.0"

  lazy val im = Project(id="im", base = file("."), settings = Defaults.coreDefaultSettings ++ sharedSettings) aggregate(projectList:_*)

  lazy val im_zookeeper = Project(id="im-zookeeper", base = file("im-zookeeper"), settings = Defaults.coreDefaultSettings ++ sharedSettings)

  lazy val im_common = Project(id = "im-common", base = file("im-common"),
                               settings = Defaults.coreDefaultSettings ++ sharedSettings)

  lazy val im_channel = Project(id = "im-channel", base = file("im-channel"),settings = Defaults.coreDefaultSettings ++ sharedSettings)

  lazy val projectList = Seq[sbt.ProjectReference](im_zookeeper, im_common, im_channel)


  val sharedSettings = Seq(
    version := libVersion,
    organization := "gome",
    scalaVersion := "2.12.3",
    libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.5.3",
    scalacOptions := Seq(
      "-target:jvm-1.8",
      "-unchecked",
      "-feature",
      "-language:_",
      "-encoding", "utf8",
      "-Xlint:-missing-interpolator",
      "-Ypatmat-exhaust-depth", "40"
    ),
    javacOptions ++= Seq("-Xlint:unchecked", "-source", "1.8", "-target", "1.8"),
    javacOptions in doc := Seq("-source", "1.8"),
    resourceGenerators in Compile += Def.task {
      val file = (resourceManaged in Compile).value / "demo" / "build.properties"
      val contents = "name=%s\nversion=%s".format(name.value,version.value)
      IO.write(file, contents)
      Seq(file)
    }.taskValue
  )

}
