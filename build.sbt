name := "interview-airdna"

version := "0.1"

scalaVersion := "2.12.8"
val sparkVersion = "3.2.0"

idePackagePrefix := Some("org.interview.airdna")

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "com.amazon.deequ" % "deequ" % "2.0.1-spark-3.2"
