package sfc.pieces

import sfc.placement.Vertex
import play.api.libs.json._

/**
 * @author noel.yap@gmail.com
 */
class Chits(val chits: Int*) {
  override def hashCode: Int = chits.foldLeft(0) { (accum, chit) =>
    41 * (41 + accum) + chit
  }
  override def equals(other: Any): Boolean = other match {
    case that: Chits => (that canEqual this) && this.chits == that.chits
    case _ => false
  }
  def canEqual(other: Any) = other.isInstanceOf[Chits]
  override def toString = "[" + (chits mkString ",") + "]"
  def toJson: JsValue = {
    JsArray(chits map { chit =>
      Json.toJson(chit)
    })
  }

  def odds: Int = {
    (chits map { chit =>
      math.max(0, Vertex.maxId - math.abs(Vertex.maxId - chit + 1))
    }).sum
  }
}

object Chits {
  def apply(chits: Int*) = new Chits(chits: _*)
}