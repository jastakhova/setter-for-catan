package sfc.board

import akka.actor.{Actor, ActorRef}
import akka.util.Timeout
import scala.concurrent.duration._
import sfc.board.GenerateBoardActor.GenerateBoard

/**
 * @author noel.yap@gmail.com
 */
// TODO: use FSM
class ValidBoardActor extends Actor {
  private implicit val timeout = Timeout(12.seconds)

  private lazy val generateBoardActor = context.actorFor(s"/user/${GenerateBoardActor.name}")

  private def stopProcessing: Receive = {
    case _ =>
  }

  private def processBoards(requester: ActorRef, piecesConfigSpec: Configuration.PiecesConfigSpec*): Receive = {
    case board: Board => {
      if (board.check) {
        requester ! board

        context.become(stopProcessing)
      } else {
        self ! GenerateBoard(piecesConfigSpec: _*)

        context.become(processRequests(requester))
      }
    }
  }

  private def processRequests(requester: ActorRef): Receive = {
    case GenerateBoard(configuration @ _*) => {
      generateBoardActor ! GenerateBoard(configuration: _*)

      context.become(processBoards(requester, configuration: _*))
    }
  }

  def receive = {
    case message => {
      self forward message

      context.become(processRequests(sender))
    }
  }
}

object ValidBoardActor {
  val name = "validBoard"
}