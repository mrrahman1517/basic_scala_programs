// Minimal Akka Actor example
// Add the following dependency to your build.sbt if using SBT:
// libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0"

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object HelloActor {
  def apply(): Behavior[String] = Behaviors.receive { (context, message) =>
    context.log.info(s"Received message: $message")
    Behaviors.same
  }
}

object Main extends App {
  val system = ActorSystem(HelloActor(), "hello-actor-system")
  system ! "Hello, Actor!"
  Thread.sleep(1000)
  system.terminate()
}