package sfc.board

import akka.actor.{Props, ActorSystem}
import akka.pattern.ask
import akka.routing.SmallestMailboxRouter
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

// TODO: use config file
object ValidCount {
  // FIXME: OOM if currentSampleSize is too large
  // private val sampleSize: Int = math.pow(6, 6).round.asInstanceOf[Int]
  private val sampleSize: Int = math.pow(5, 5).round.asInstanceOf[Int]

  private implicit val timeout = Timeout((sampleSize / 648).seconds)

  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): (Int => Pair[Int, Int]) = { sampleSize: Int =>
    val system = ActorSystem("SetterForCatan")

    try {
      val validCountActor = system.actorOf(Props(new ValidCountActor(sampleSize, piecesConfigSpec: _*)), "validCount")

      // TODO: return `Future` rather than calling `Await.result`
      val count = validCountActor ? ValidCountActor.GetResult
      Await.result(count.mapTo[Pair[Int, Int]], timeout.duration)
    } finally {
      system.shutdown()
    }
  }
}
