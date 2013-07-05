package sfc.board

import sfc.pieces.Tile._
import sfc.pieces.Chits
import sfc.placement.{HexagonPosition, Coordinate}

/**
 * @author noel.yap@gmail.com
 */
object SmallTradersAndBarbariansBoard {
  val resourceTilesRemoved = IndexedSeq(Field, Pasture)
  val resourceTiles = SmallBoard.resourceTiles diff resourceTilesRemoved

  val resourceChitsRemoved = IndexedSeq(Chits(2), Chits(12))
  val resourceChits = SmallBoard.resourceChits diff resourceChitsRemoved

  val hexagonCoordinatesRemoved = IndexedSeq(Coordinate(0, 2), Coordinate(0, -2), Coordinate(2, -2))
  val hexagonCoordinates: IndexedSeq[Coordinate] = SmallBoard.hexagonCoordinates diff hexagonCoordinatesRemoved

  val hexagonPositions: IndexedSeq[HexagonPosition] = hexagonCoordinates map {
    new HexagonPosition(_)
  }

  val hexagonPiecesConfiguration: Configuration.PiecesConfigSpec = (
    hexagonPositions,
    IndexedSeq(
      (resourceTiles, resourceChits)))
  val trianglePiecesConfiguration = SmallBoard.trianglePiecesConfiguration

  def board = ValidBoard(hexagonPiecesConfiguration, trianglePiecesConfiguration)

  val sampleSize = math.pow(6, 6).round
  def count = ValidCount(hexagonPiecesConfiguration, trianglePiecesConfiguration)(sampleSize.asInstanceOf[Int])
  def probability = count.asInstanceOf[Double] / sampleSize
}
