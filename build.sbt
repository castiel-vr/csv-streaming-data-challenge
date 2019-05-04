
name := "csv-streaming-data-challenge"

version := "0.1"

scalaVersion := "2.12.8"

val scalatestVersion = "3.0.5"
val typesafeConfigVersion = "1.3.2"
val commonsCliVersion = "1.2"
val alpakkaVersion = "1.0.0"

libraryDependencies += "org.scalactic" %% "scalactic" % scalatestVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalatestVersion % "test"
libraryDependencies += "com.typesafe" % "config" % typesafeConfigVersion
libraryDependencies += "commons-cli" % "commons-cli" % commonsCliVersion
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-udp" % alpakkaVersion
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-file" % alpakkaVersion

libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.21" % Test

assemblyMergeStrategy in assembly := {
  case PathList("org","aopalliance", xs @ _*) => MergeStrategy.last
  case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
  case "META-INF/mailcap" => MergeStrategy.last
  case "META-INF/mimetypes.default" => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
