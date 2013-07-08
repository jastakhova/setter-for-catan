package sfc.board

import akka.actor.Actor

/**
 * @author noel.yap@gmail.com
 */
class GenerateBoardActor extends Actor {
  import GenerateBoardActor._

  def receive = {
    case configuration: Configuration => {
      sender ! Board(configuration.configuration: _*)
    }
  }
}

object GenerateBoardActor {
  case class Configuration(configuration: sfc.board.Configuration.PiecesConfigSpec*)
}