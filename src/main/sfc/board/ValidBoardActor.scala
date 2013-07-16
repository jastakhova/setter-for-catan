package sfc.board

import akka.actor.Actor

/**
 * @author noel.yap@gmail.com
 */
class ValidBoardActor extends Actor {
  import ValidBoardActor._

  private def processResult(board: Board): Receive = {
    case board: Board => {
      context.stop(sender)
    }

    case GetResult => {
      sender ! board
    }
  }

  private def processBoards: Receive = {
    case board: Board => {
      if (board.check) {
        // TODO: kill sender parent
        context.stop(context.actorFor(sender.path.parent))

        context.become(processResult(board))
      } else {
        self forward board.configuration
      }
    }

    case GetResult => {
      self.tell(GetResult, sender)
    }
  }

  def receive = processBoards
}

object ValidBoardActor {
  case object GetResult
}