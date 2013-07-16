package sfc.board

import akka.actor.Actor

/**
 * @author noel.yap@gmail.com
 */
class ValidCountActor extends Actor {
  import ValidCountActor._

  def receive = {
    case board: Board => {
      if (board.check) {
        result += 1
      }

      count += 1
    }

    case GetResult(expectedCount) => {
      // TODO: use stash
      if (count == expectedCount) {
        sender ! Pair(result, count)
      } else {
        self forward GetResult(expectedCount)
      }
    }
  }
}

object ValidCountActor {
  case class GetResult(expectedCount: Int)

  // TODO: remove use of var
  var result = 0
  var count = 0
}