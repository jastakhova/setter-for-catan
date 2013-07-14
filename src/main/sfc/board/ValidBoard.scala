package sfc.board

import akka.actor.{Props, ActorSystem}
import akka.pattern.ask
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout

/**
 * @author noel.yap@gmail.com
 */
object ValidBoard {
  private implicit val timeout = Timeout(12.seconds)

  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): Board = {
    val system = ActorSystem("SetterForCatan")

    try {
      val validBoardActor = system.actorOf(Props[ValidBoardActor], "validBoardActor")

      val board = validBoardActor ? ValidBoardActor.GetResult

      val generateBoardActor = system.actorOf(Props[GenerateBoardActor], "generateBoardActor")

      val numberOfGenerators = (math.log(.5)/math.log(1 - .0005)).round.asInstanceOf[Int]
      generateBoardActor.tell(
        GenerateBoardActor.GenerateBoards(numberOfGenerators, piecesConfigSpec: _*), validBoardActor)

      Await.result(board.mapTo[Board], timeout.duration)
    } finally {
      system.shutdown()
    }
  }
}
