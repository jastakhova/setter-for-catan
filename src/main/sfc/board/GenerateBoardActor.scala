package sfc.board

import akka.actor.{Props, Actor}

/**
 * @author noel.yap@gmail.com
 */
class GenerateBoardActor extends Actor {
  import GenerateBoardActor._

  def receive = {
    case GenerateBoards(numberOfGenerators, configuration @ _*) => {
      if (numberOfGenerators > 0) {
        // TODO: stop children when self stops
        val generateBoardActor = context.actorOf(Props[GenerateBoardActor], s"${numberOfGenerators}")

        generateBoardActor.tell(GenerateBoard(configuration: _*), sender)

        self.tell(GenerateBoards(numberOfGenerators - 1, configuration: _*), sender)
      }
    }

    case GenerateBoard(configuration @ _*) => {
      sender ! Board(configuration: _*)
    }
  }
}

object GenerateBoardActor {
  case class GenerateBoards(numberOfGenerators: Int, configuration: Configuration.PiecesConfigSpec*)
  case class GenerateBoard(configuration: Configuration.PiecesConfigSpec*)
}