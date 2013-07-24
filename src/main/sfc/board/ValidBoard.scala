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
// TODO: use config file
object ValidBoard {
  private implicit val timeout = Timeout(12.seconds)

  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): Board = {
    val system = ActorSystem("SetterForCatan")

    val numberOfGenerators = (math.log(.5)/math.log(1 - .0005)).round.asInstanceOf[Int]

    try {
      // TODO: have `validBoardActor` create `generateBoardActor`
      // TODO: set `supervisorStrategy` to resume children
      // TODO: use `BalancingDispatcher` instead of `SmallestMailboxRouter`
      // TODO: allow resizing of number of routees
      val generateBoardActor = system.actorOf(
        Props[GenerateBoardActor].withRouter(SmallestMailboxRouter(nrOfInstances = numberOfGenerators)),
        GenerateBoardActor.name)
      // TODO: set `supervisorStrategy` to resume children
      // TODO: allow resizing of number of routees
      val validBoardActor = system.actorOf(
        Props[ValidBoardActor].withRouter(
          ScatterGatherFirstCompletedRouter(nrOfInstances = numberOfGenerators, within = timeout.duration)),
        ValidBoardActor.name)

      // TODO: return `Future` rather than calling `Await.result`
      val board = validBoardActor ? GenerateBoardActor.GenerateBoard(piecesConfigSpec: _*)
      Await.result(board.mapTo[Board], timeout.duration)
    } finally {
      system.shutdown()
    }
  }
}
