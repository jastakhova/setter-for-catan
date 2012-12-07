package sfc.placement

import play.api.libs.json.JsObject
import sfc.placement.EdgeOrientation._
import sfc.placement.Vertex._

/**
 * @author noel.yap@gmail.com
 */
class TrianglePosition(coordinate: Coordinate, val orientation: EdgeOrientation)
    extends Position(coordinate) {
  override def hashCode = 41 * super.hashCode + orientation.hashCode
  override def equals(other: Any): Boolean = other match {
    case that: TrianglePosition => (that canEqual this) && super.equals(that) && orientation == that.orientation
    case _ => false
  }
  override def canEqual(rhs: Any) = rhs.isInstanceOf[TrianglePosition]
  override val toString = "(" + coordinate.x + "," + coordinate.y + "," + orientation + ")"
  override def toJson = super.toJson ++ JsObject(Seq(("orientation", orientation.toJson)))
  override val vertices: Set[Vertex] = {
    val vertex: Vertex = orientation match {
      case EdgeOrientation.TopRight => Vertex.Top
      case EdgeOrientation.Right => Vertex.TopRight
      case EdgeOrientation.BottomRight => Vertex.BottomRight
      case EdgeOrientation.BottomLeft => Vertex.Bottom
      case EdgeOrientation.Left => Vertex.BottomLeft
      case EdgeOrientation.TopLeft => Vertex.TopLeft
    }

    (vertex to vertex.after).toSet
  }
}
