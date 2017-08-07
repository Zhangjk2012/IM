import sbt._
import Keys._

/**
  *
  * Created by ZJK on 2017/8/7.
  */
object Dependencies {
  val akkaVersion = "2.5.3"
  val akkaHttpVersion = "10.0.9"
  val playJsonVersion = "2.6.2"


  val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"



  val jacksonVersion = "2.8.9"

  val jacksons = Seq(
    "com.fasterxml.jackson.core" % "jackson-core",
    "com.fasterxml.jackson.core" % "jackson-annotations",
    "com.fasterxml.jackson.core" % "jackson-databind",
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8",
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310"
  ).map(_ % jacksonVersion)


  val slf4jVersion = "1.7.25"
  val slf4j = Seq("slf4j-api", "jul-to-slf4j", "jcl-over-slf4j").map("org.slf4j" % _ % slf4jVersion)
  val slf4jSimple = "org.slf4j" % "slf4j-simple" % slf4jVersion

  val guava = "com.google.guava" % "guava" % "22.0"
  val findBugs = "com.google.code.findbugs" % "jsr305" % "3.0.2"

  val joda = Seq(
    "joda-time" % "joda-time" % "2.9.9",
    "org.joda" % "joda-convert" % "1.8.2"
  )

  val nettyVersion = "4.1.13.Final"





}
