package sfc.placement

import play.api.libs.json.{Json, JsValue}

/**
 * @author noel.yap@gmail.com
 */
class Coordinate(val x: Int, val y: Int) {
  override def hashCode = 41 * (41 + x) + y
  override def equals(other: Any): Boolean = other match {
    case that: Coordinate => (that canEqual this) && this.x == that.x && this.y == that.y
    case _ => false
  }
  def canEqual(other: Any) = other.isInstanceOf[Coordinate]
  def toJson: JsValue = {
    Json.toJson(Map(
      ("x", x),
      ("y", y)
    ))
  }

  def translate(dx: Int = 0, dy: Int = 0) = Coordinate(x + dx, y + dy)

  def upperLeftAdjacent = translate(dx = +1)
  def upperRightAdjacent = translate(dy = +1)
  def rightAdjacent = translate(dx = -1, dy = +1)
  def lowerLeftAdjacent = translate(dy = -1)
  def lowerRightAdjacent = translate(dx = -1)
  def leftAdjacent = translate(dx = +1, dy = -1)
}

object Coordinate {
  def apply(x: Int, y: Int) = new Coordinate(x, y)
}