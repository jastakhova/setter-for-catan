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
      if (count == expectedCount) {
        sender ! Pair(result, count)
      } else {
        self.tell(GetResult(expectedCount), sender)
      }
    }
  }
}

object ValidCountActor {
  case class GetResult(expectedCount: Int)

  var result = 0
  var count = 0
}