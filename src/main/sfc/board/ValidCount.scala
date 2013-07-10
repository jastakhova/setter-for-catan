package sfc.board

import akka.actor.{Props, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Random

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
  // FIXME: OOM if sampleSize is too large
  private val sampleSize: Int = math.pow(6, 6).round.asInstanceOf[Int]

  private implicit val timeout = Timeout((sampleSize / 648).seconds)

  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): (Int => Pair[Int, Int]) = { sampleSize: Int =>
    val numberOfGenerators = math.min(sampleSize, 127)
    val system = ActorSystem("SetterForCatan")
    val validCountActor = system.actorOf(Props[ValidCountActor], "validCountActor")

    for (i <- 0 until numberOfGenerators) {
      system.actorOf(Props[GenerateBoardActor], s"generateBoardActor.${i}")
    }

    for (i <- 1 to sampleSize) {
      val generateBoardActor = system.actorFor(s"user/generateBoardActor.${Random.nextInt(numberOfGenerators)}")

      generateBoardActor.tell(GenerateBoardActor.Configuration(piecesConfigSpec: _*), validCountActor)
    }

    val count = validCountActor ? ValidCountActor.GetResult(sampleSize)
    val result = Await.result(count.mapTo[Pair[Int, Int]], timeout.duration)

    // FIXME: hangs upon timeout
    system.shutdown()

    result
  }
}
