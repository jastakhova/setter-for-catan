package sfc.placement

import play.api.libs.json.JsObject
import sfc.placement.VertexOrientation._
import sfc.placement.Vertex._

/**
 * @author noel.yap@gmail.com
 */
class ChevronPosition(coordinate: Coordinate, val orientation: VertexOrientation)
    extends Position(coordinate) {
  override def hashCode = 41 * super.hashCode + orientation.hashCode
  override def equals(other: Any): Boolean = other match {
    case that: ChevronPosition => (that canEqual this) && super.equals(that) && orientation == that.orientation
    case _ => false
  }
  override def canEqual(rhs: Any) = rhs.isInstanceOf[ChevronPosition]
  override val toString = "(" + coordinate.x + "," + coordinate.y + "," + orientation + ")"
  override def toJson = super.toJson ++ JsObject(Seq(("orientation", orientation.toJson)))
  override val vertices: Set[Vertex] = {
    val vertex: Vertex = orientation match {
      case VertexOrientation.Top => Vertex.Top
      case VertexOrientation.TopRight => Vertex.TopRight
      case VertexOrientation.BottomRight => Vertex.BottomRight
      case VertexOrientation.Bottom => Vertex.Bottom
      case VertexOrientation.BottomLeft => Vertex.BottomLeft
      case VertexOrientation.TopLeft => Vertex.TopLeft
    }

    (vertex.before to vertex.after).toSet
  }
}

object ChevronPosition {
  def adjacentPositions(intersection: Intersection): Set[Position] = {
    val positionArgs: Set[(Coordinate, VertexOrientation)] = intersection.adjacentPositionArgs({ intersection =>
      Set(
        (intersection.belowCoordinate, VertexOrientation.Top),
        (intersection.upperLeftCoordinate, VertexOrientation.BottomRight),
        (intersection.upperRightCoordinate, VertexOrientation.BottomLeft))
    }, { intersection =>
      Set(
        (intersection.aboveCoordinate, VertexOrientation.Bottom),
        (intersection.lowerLeftCoordinate, VertexOrientation.TopRight),
        (intersection.lowerRightCoordinate, VertexOrientation.TopLeft))
    })

    positionArgs flatMap { args =>
      val coordinate = args._1
      val orientation = args._2

      orientation.before to orientation.after map { o =>
        new ChevronPosition(coordinate, o)
      }
    }
  }
}