package sfc.board

import akka.actor.{Props, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * @author noel.yap@gmail.com
 */
abstract class ValidCount {
  import ValidCount._

  protected def count: (Int => Pair[Int, Int])
  def probability: Double = {
    val c = count(sampleSize)

    c._1.asInstanceOf[Double] / c._2
  }
}

object ValidCount {
  private val sampleSize: Int = math.pow(6, 6).round.asInstanceOf[Int]

  private implicit val timeout = Timeout(12.seconds)

  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): (Int => Pair[Int, Int]) = { sampleSize: Int =>
    val system = ActorSystem("SetterForCatan")
    val validCountActor = system.actorOf(Props[ValidCountActor], s"validCountActor")

    for (i <- 1 to sampleSize) {
      val generateBoardActor = system.actorOf(Props[GenerateBoardActor], s"generateBoardActor.${i}")

      generateBoardActor.tell(GenerateBoardActor.Configuration(piecesConfigSpec: _*), validCountActor)
    }

    val result = Await.result((validCountActor ? ValidCountActor.GetResult).mapTo[Pair[Int, Int]], 12.seconds)

    system.shutdown()

    result
  }
}
