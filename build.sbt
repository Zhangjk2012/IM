name := "SBTTest"

version := "1.0"

scalaVersion := "2.12.3"

val sharedSettings = Seq(
  libraryDependencies ++= Seq("com.typesafe.akka" % "akka-actor_2.12" % "2.5.3",
    "com.google.protobuf" % "protobuf-java" % "3.3.1"
  ),
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
    val file = (resourceManaged in Compile).value / name.value / "build.properties"
    val log = streams.value.log
    val buildRev = Process("git" :: "rev-parse" :: "HEAD" :: Nil).!!.trim
    val buildName = new java.text.SimpleDateFormat("yyyyMMdd-HHmmss").format(new java.util.Date)
    //      val contents = "name=%s\nversion=%s".format(name.value,version.value)
    val contents = s"name=${name.value}\nversion=${version.value}\nbuild_revision=$buildRev\nbuild_name=$buildName"
    IO.write(file, contents)
    Seq(file)
  }.taskValue
)

val protoBuildTask = taskKey[Unit]("This is a protobuf build task.")

lazy val im = Project(id="im", base = file("."), settings = Defaults.coreDefaultSettings ++ sharedSettings) aggregate(projectList:_*)

lazy val im_zookeeper = Project(id="im-zookeeper", base = file("im-zookeeper"), settings = Defaults.coreDefaultSettings ++ sharedSettings)

lazy val im_common = Project(id = "im-common", base = file("im-common"),
  settings = Defaults.coreDefaultSettings ++ sharedSettings)

lazy val im_channel = Project(id = "im-channel", base = file("im-channel"), settings = Defaults.coreDefaultSettings ++ sharedSettings)

lazy val im_protobuf = Project(id = "im-protobuf", base = file("im-protobuf"),
  settings = Defaults.coreDefaultSettings ++ sharedSettings).settings(
  protoBuildTask := {
    val srcDir = (sourceDirectory in Compile).value
    val destDir = s"${srcDir}/java"
    val protoDir = s"${srcDir}/protobuf"
    val protoFile = s"${srcDir}/protobuf/test.proto"

    val commandLines = List("E:\\software\\work\\protoc-3.3.0-win32\\bin\\protoc","--proto_path" ,protoDir,"--java_out", destDir, protoFile)
    val build  = Process(commandLines).!!
    println(build)
  }
).settings(
  libraryDependencies += "com.google.protobuf" % "protobuf-java" % "3.3.1"
)

lazy val projectList = Seq[sbt.ProjectReference](im_zookeeper, im_common, im_channel, im_protobuf)
