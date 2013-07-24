package sfc.board

import akka.actor.{Props, Actor}
import akka.routing.SmallestMailboxRouter

/**
 * @author noel.yap@gmail.com
 */
class ValidCountActor(sampleSize: Int, piecesConfigSpec: Configuration.PiecesConfigSpec*) extends Actor {
  import ValidCountActor._

  override def preStart() {
    val numberOfGenerators = math.min(sampleSize, 127)
    // TODO: set `supervisorStrategy` to resume children
    // TODO: use `BroadcastRouter`
    // TODO: allow resizing of number of routees
    val generateBoardActor = context.actorOf(
      Props[GenerateBoardActor].withRouter(SmallestMailboxRouter(nrOfInstances = numberOfGenerators)),
      "generateBoard")
    // TODO: have `generateBoard` constantly generate boards until stopped; expected to fix OOM
    for (i <- 1 to sampleSize) {
      generateBoardActor ! GenerateBoardActor.GenerateBoard(piecesConfigSpec: _*)
    }
  }

  private def processMessages(numberOfValidBoards: Int, currentSampleSize: Int): Receive = {
    case board: Board => {
      implicit def booleanToInt(b: Boolean) = if (b) {
        1
      } else {
        0
      }

      context.become(processMessages(numberOfValidBoards + board.check, currentSampleSize + 1))
    }

    case GetResult => {
      // TODO: use stash
      if (currentSampleSize == sampleSize) {
        sender ! Pair(numberOfValidBoards, currentSampleSize)
      } else {
        self forward GetResult
      }
    }
  }

  def receive = processMessages(0, 0)
}

object ValidCountActor {
  case object GetResult
}