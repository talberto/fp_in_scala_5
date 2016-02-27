scalaVersion := "2.11.7"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.6"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.4.6"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.2"
libraryDependencies += "com.typesafe.akka" %% "akka-http-experimental" % "2.4.2"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"
libraryDependencies += "org.mockito" % "mockito-core" % "1.10.19" % "test"