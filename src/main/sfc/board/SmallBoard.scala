package sfc.board

import sfc.pieces.Tile._
import sfc.pieces.Chits
import sfc.placement.{TrianglePosition, HexagonPosition, Coordinate}
import sfc.placement.EdgeOrientation._

/**
 * @author noel.yap@gmail.com
 */
object SmallBoard {
  val desertTiles = IndexedSeq(Desert)
  val resourceTiles: IndexedSeq[Tile] = (
    Field * 4 ++
    Forest * 4 ++
    Hill * 3 ++
    Mountain * 3 ++
    Pasture * 4)
  val portTiles: IndexedSeq[Tile] = (
    AnyPort * 4 ++ IndexedSeq(
      BrickPort,
      GrainPort,
      LumberPort,
      OrePort,
      WoolPort))

  val desertChits = IndexedSeq(Chits(0))
  val resourceChits = IndexedSeq(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12) map {
    Chits(_)
  }
  val portChits: IndexedSeq[Chits] = ((0 until 9) map { _ =>
    Chits(0)
  }).toIndexedSeq

  val hexagonCoordinates: IndexedSeq[Coordinate] = IndexedSeq(
    (0, 0),
    (1, 0),
    (0, 1),
    (-1, 1),
    (-1, 0),
    (0, -1),
    (1, -1),
    (2, 0),
    (1, 1),
    (0, 2),
    (-1, 2),
    (-2, 2),
    (-2, 1),
    (-2, 0),
    (-1, -1),
    (0, -2),
    (1, -2),
    (2, -2),
    (2, -1)
  ) map { xy =>
    Coordinate(xy._1, xy._2)
  }
  val triangleCoordinates: IndexedSeq[(Coordinate, EdgeOrientation)] = IndexedSeq(
    (1, 2, BottomLeft),
    (-1, 3, BottomLeft),
    (-3, 3, Left),
    (-3, 1, TopLeft),
    (-2, -1, TopLeft),
    (0, -3, TopRight),
    (2, -3, Right),
    (3, -2, Right),
    (3, 0, BottomRight)
  ) map { xyo =>
    (Coordinate(xyo._1, xyo._2), xyo._3)
  }

  val hexagonPositions: IndexedSeq[HexagonPosition] = hexagonCoordinates map {
    new HexagonPosition(_)
  }
  val trianglePositions: IndexedSeq[TrianglePosition] = triangleCoordinates map { co =>
    val coordinate: Coordinate = co._1
    val orientation: EdgeOrientation = co._2

    new TrianglePosition(coordinate, orientation)
  }

  val hexagonPiecesConfiguration: ValidBoard.PiecesConfigSpec = (
    hexagonPositions,
    IndexedSeq(
      (desertTiles, desertChits),
      (resourceTiles, resourceChits)))
  val trianglePiecesConfiguration: ValidBoard.PiecesConfigSpec = (
    trianglePositions,
    IndexedSeq(
      (portTiles, portChits)))

  def board = ValidBoard(hexagonPiecesConfiguration, trianglePiecesConfiguration)

  val sampleSize = math.pow(6, 6).round
  def count = ValidCount(hexagonPiecesConfiguration, trianglePiecesConfiguration)(sampleSize.asInstanceOf[Int])
  def probability = count.asInstanceOf[Double] / sampleSize
}
