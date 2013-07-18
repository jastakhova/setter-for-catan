package sfc.board

import akka.actor.Actor

/**
 * @author noel.yap@gmail.com
 */
class GenerateBoardActor extends Actor {
  import GenerateBoardActor._

  def receive = {
    case GenerateBoard(configuration @ _*) => {
      sender ! Board(configuration: _*)
    }
  }
}

object GenerateBoardActor {
  case class GenerateBoard(configuration: Configuration.PiecesConfigSpec*)

  val name = "generateBoard"
}