package sfc.placement

/**
 * @author noel.yap@gmail.com
 */
class HexagonPosition(coordinate: Coordinate) extends Position(coordinate) {
  override def hashCode = 41 * super.hashCode
  override def equals(other: Any): Boolean = other match {
    case that: HexagonPosition => (that canEqual this) && super.equals(that)
    case _ => false
  }
  override def canEqual(rhs: Any) = rhs.isInstanceOf[HexagonPosition]
  override val toString = "(" + coordinate.x + "," + coordinate.y + ")"
  override val vertices = Vertex.values
}

object HexagonPosition {
  def adjacentPositions(intersection: Intersection): Set[Position] = {
    val adjacentCoordinates: Set[Coordinate] = intersection.adjacentPositionArgs({ intersection =>
      Set(
        intersection.belowCoordinate,
        intersection.upperLeftCoordinate,
        intersection.upperRightCoordinate)
    }, { intersection =>
      Set(
        intersection.aboveCoordinate,
        intersection.lowerLeftCoordinate,
        intersection.lowerRightCoordinate)
    })

    adjacentCoordinates map { coordinate =>
      new HexagonPosition(coordinate)
    }
  }
}