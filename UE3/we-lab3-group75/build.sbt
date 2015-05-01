name := """we-lab3-group75"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
    javaJdbc,
    javaCore,
    javaJpa,
    "org.hibernate" % "hibernate-entitymanager" % "4.3.1.Final",
    "com.google.code.gson" % "gson" % "2.2"
)
