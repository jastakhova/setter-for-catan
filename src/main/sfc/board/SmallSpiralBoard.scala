package sfc.board

import sfc.pieces.Tile._
import sfc.pieces.Chits
import sfc.placement.{VertexOrientation, ChevronPosition, HexagonPosition, Coordinate}
import sfc.placement.VertexOrientation._
import sfc.placement.VertexOrientation.VertexOrientation

/**
 * @author noel.yap@gmail.com
 */
// TODO: use config file
object SmallSpiralBoard extends ValidCount {
  val desertTiles = SmallBoard.desertTiles
  val resourceTilesAdded = GoldField * 2
  val resourceTiles = SmallBoard.resourceTiles ++ resourceTilesAdded
  val lakeTiles = IndexedSeq(Lake)
  val fisheryTiles = Fishery * 6

  val desertChits = SmallBoard.desertChits
  val resourceChitsAdded = IndexedSeq(Chits(2), Chits(12))
  val resourceChits: IndexedSeq[Chits] = SmallBoard.resourceChits ++ resourceChitsAdded
  val lakeChits = IndexedSeq(Chits(2, 3, 11, 12))
  val fisheryChits: IndexedSeq[Chits] = IndexedSeq(4, 5, 6, 8, 9, 10) map {
    Chits(_)
  }

  val hexagonCoordinatesAdded = IndexedSeq(Coordinate(2, 1), Coordinate(-3, 2), Coordinate(1, -3))
  val hexagonCoordinates: IndexedSeq[Coordinate] = SmallBoard.hexagonCoordinates ++ hexagonCoordinatesAdded
  val chevronCoordinates: IndexedSeq[(Coordinate, VertexOrientation)] = IndexedSeq(
    (2, 1, Top),
    (-2, 3, BottomLeft),
    (-3, 2, BottomRight),
    (-1, -2, Top),
    (1, -3, BottomLeft),
    (3, -1, BottomRight)
  ) map { xyo =>
    (Coordinate(xyo._1, xyo._2), xyo._3)
  }

  val hexagonPositions: IndexedSeq[HexagonPosition] = hexagonCoordinates map {
    new HexagonPosition(_)
  }
  val chevronPositions: IndexedSeq[ChevronPosition] = chevronCoordinates map { co =>
    val coordinate: Coordinate = co._1
    val orientation: VertexOrientation.VertexOrientation = co._2

    new ChevronPosition(coordinate, orientation)
  }

  val hexagonPiecesConfiguration: Configuration.PiecesConfigSpec = (
    hexagonPositions,
    IndexedSeq(
      (desertTiles, desertChits),
      (resourceTiles, resourceChits),
      (lakeTiles, lakeChits)))
  val chevronPiecesConfiguration: Configuration.PiecesConfigSpec = (
    chevronPositions,
    IndexedSeq(
      (fisheryTiles, fisheryChits)))
  val trianglePiecesConfiguration = SmallBoard.trianglePiecesConfiguration

  def board: Board = ValidBoard(hexagonPiecesConfiguration, chevronPiecesConfiguration, trianglePiecesConfiguration)
  protected override def count = ValidCount(hexagonPiecesConfiguration, chevronPiecesConfiguration, trianglePiecesConfiguration)
}
