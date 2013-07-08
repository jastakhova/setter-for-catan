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

    case GetResult => {
      sender ! Pair(result, count)
    }
  }
}

object ValidCountActor {
  case object GetResult

  var result = 0
  var count = 0
}