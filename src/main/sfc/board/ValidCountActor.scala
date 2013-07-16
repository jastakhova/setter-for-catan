package sfc.board

import akka.actor.Actor

/**
 * @author noel.yap@gmail.com
 */
class ValidCountActor extends Actor {
  import ValidCountActor._

  private def processMessages(numberOfValidBoards: Int, sampleSize: Int): Receive = {
    case board: Board => {
      implicit def booleanToInt(b: Boolean) = if (b) {
        1
      } else {
        0
      }

      context.become(processMessages(numberOfValidBoards + board.check, sampleSize + 1))
    }

    case GetResult(expectedSampleSize) => {
      // TODO: use stash
      if (sampleSize == expectedSampleSize) {
        sender ! Pair(numberOfValidBoards, sampleSize)
      } else {
        self forward GetResult(expectedSampleSize)
      }
    }
  }

  def receive = processMessages(0, 0)
}

object ValidCountActor {
  case class GetResult(expectedCount: Int)
}