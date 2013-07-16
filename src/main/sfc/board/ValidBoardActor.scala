package sfc.board

import akka.actor.{Kill, Actor}

/**
 * @author noel.yap@gmail.com
 */
class ValidBoardActor extends Actor {
  import ValidBoardActor._

  private def processResult(board: Board): Receive = {
    case GetResult => {
      sender ! board
    }
  }

  private def processBoards: Receive = {
    case board: Board => {
      if (board.check) {
        val generateBoardActor = context.actorFor(sender.path.parent)
        generateBoardActor ! Kill

        context.become(processResult(board))
      } else {
        self forward board.configuration
      }
    }

    case GetResult => {
      // TODO: use stash
      self.tell(GetResult, sender)
    }
  }

  def receive = processBoards
}

object ValidBoardActor {
  case object GetResult
}