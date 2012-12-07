package sfc.placement

import sfc.placement.Vertex.Vertex

class Intersection(val x: Int, val y: Int) {
  override def toString = "(" + x + "," + y + ")"
  val hasAboveAdjacent = x % 2 != y % 2
  def adjacentPositionArgs[T](
      whenTopOfHexagon: Intersection => Set[T], whenBottomOfHexagon: Intersection => Set[T]): Set[T] = {

    if (hasAboveAdjacent) {
      whenBottomOfHexagon(this)
    } else {
      whenTopOfHexagon(this)
    }
  }
  lazy val belowCoordinate: Coordinate = {
    assert(!hasAboveAdjacent)

    Coordinate((y - x) / 2, (y + x) / 2)
  }
  lazy val upperLeftCoordinate: Coordinate = {
    assert(!hasAboveAdjacent)

    belowCoordinate.upperLeftAdjacent
  }
  lazy val upperRightCoordinate: Coordinate = {
    assert(!hasAboveAdjacent)

    belowCoordinate.upperRightAdjacent
  }
  lazy val aboveCoordinate: Coordinate = {
    assert(hasAboveAdjacent)

    Coordinate((y - x + 1) / 2, (y + x + 1) / 2)
  }
  lazy val lowerLeftCoordinate: Coordinate = {
    assert(hasAboveAdjacent)

    aboveCoordinate.lowerLeftAdjacent
  }
  lazy val lowerRightCoordinate: Coordinate = {
    assert(hasAboveAdjacent)

    aboveCoordinate.lowerRightAdjacent
  }
}

object Intersection {
  def apply(coordinate: Coordinate, vertex: Vertex): Intersection = {
    val xBase = coordinate.y - coordinate.x
    val xOffset: Int = if (vertex.isOnRight) {
      1
    } else if (vertex.isOnLeft) {
      -1
    } else {
      0
    }
    val yBase: Int = coordinate.y + coordinate.x
    val yOffset: Int = if (vertex.isOnBottom) {
      -1
    } else {
      0
    }

    new Intersection(xBase + xOffset, yBase + yOffset)
  }
}






