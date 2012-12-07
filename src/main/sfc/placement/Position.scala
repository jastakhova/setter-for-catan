package sfc.placement

import play.api.libs.json.JsObject
import sfc.placement.Vertex._

/**
 * @author noel.yap@gmail.com
 */
abstract class Position(val coordinate: Coordinate) {
  override def hashCode = 41 * (41 + coordinate.hashCode)
  override def equals(other: Any): Boolean = other match {
    case that: Position => (that canEqual this) && coordinate == that.coordinate
    case _ => false
  }
  def canEqual(rhs: Any) = rhs.isInstanceOf[Position]
  val vertices: Set[Vertex]
  def toJson = JsObject(Seq(("coordinate", coordinate.toJson)))
}

object Position {
  def adjacentCoordinates(intersection: Intersection): Set[Coordinate] = {
    val x = intersection.x
    val y = intersection.y

    val getBelowAdjacent = x % 2 == y % 2
    if (getBelowAdjacent) {
      val belowCoordinate = Coordinate((y - x) / 2, (y + x) / 2)
      val upperLeftCoordinate = belowCoordinate.upperLeftAdjacent
      val upperRightCoordinate = belowCoordinate.upperRightAdjacent

      Set(
        belowCoordinate,
        upperLeftCoordinate,
        upperRightCoordinate)
    } else {
      val aboveCoordinate = Coordinate((y - x + 1) / 2, (y + x + 1) / 2)
      val lowerLeftCoordinate = aboveCoordinate.lowerLeftAdjacent
      val lowerRightCoordinate = aboveCoordinate.lowerRightAdjacent

      Set(
        aboveCoordinate,
        lowerLeftCoordinate,
        lowerRightCoordinate)
    }
  }
}