package sfc.board

import akka.actor.{Props, ActorSystem}
import akka.pattern.ask
import akka.routing.RoundRobinRouter
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * @author noel.yap@gmail.com
 */
object ValidBoard {
  private implicit val timeout = Timeout(12.seconds)

  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): Board = {
    val system = ActorSystem("SetterForCatan")

    try {
      val validBoardActor = system.actorOf(Props[ValidBoardActor], "validBoardActor")

      val numberOfGenerators = (math.log(.5)/math.log(1 - .0005)).round.asInstanceOf[Int]
      val generateBoardActor = system.actorOf(
        Props[GenerateBoardActor].withRouter(RoundRobinRouter(nrOfInstances = numberOfGenerators)), "generateBoard")
      for (i <- 1 to numberOfGenerators) {
        generateBoardActor.tell(GenerateBoardActor.GenerateBoard(piecesConfigSpec: _*), validBoardActor)
      }

      val board = validBoardActor ? ValidBoardActor.GetResult
      Await.result(board.mapTo[Board], timeout.duration)
    } finally {
      system.shutdown()
    }
  }
}
