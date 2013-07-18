package sfc.board

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.routing.{ScatterGatherFirstCompletedRouter, SmallestMailboxRouter}
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

    val numberOfGenerators = (math.log(.5)/math.log(1 - .0005)).round.asInstanceOf[Int]

    try {
      // TODO: pass generateBoardActor into validBoardActor
      val generateBoardActor = system.actorOf(
        Props[GenerateBoardActor].withRouter(SmallestMailboxRouter(nrOfInstances = numberOfGenerators)),
        GenerateBoardActor.name)
      val validBoardActor = system.actorOf(
        Props[ValidBoardActor].withRouter(
          ScatterGatherFirstCompletedRouter(nrOfInstances = numberOfGenerators, within = timeout.duration)),
        ValidBoardActor.name)

      val board = validBoardActor ? GenerateBoardActor.GenerateBoard(piecesConfigSpec: _*)
      Await.result(board.mapTo[Board], timeout.duration)
    } finally {
      system.shutdown()
    }
  }
}
