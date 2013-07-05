package sfc.board

import sfc.placement.{VertexOrientation, ChevronPosition, HexagonPosition, Coordinate}

/**
 * @author noel.yap@gmail.com
 */
object SmallTradersAndBarbariansSpiralBoard {
  val resourceTiles = SmallTradersAndBarbariansBoard.resourceTiles ++ SmallSpiralBoard.resourceTilesAdded
  val lakeTiles = SmallSpiralBoard.lakeTiles
  val fisheryTiles = SmallSpiralBoard.fisheryTiles

  val resourceChits = SmallTradersAndBarbariansBoard.resourceChits ++ SmallSpiralBoard.resourceChitsAdded
  val lakeChits = SmallSpiralBoard.lakeChits
  val fisheryChits = SmallSpiralBoard.fisheryChits

  val hexagonCoordinates = SmallTradersAndBarbariansBoard.hexagonCoordinates ++ SmallSpiralBoard.hexagonCoordinatesAdded
  val chevronCoordinates = SmallSpiralBoard.chevronCoordinates

  val hexagonPositions: IndexedSeq[HexagonPosition] = hexagonCoordinates map {
    new HexagonPosition(_)
  }
  val chevronPositions: IndexedSeq[ChevronPosition] = chevronCoordinates map { co =>
    val coordinate: Coordinate = co._1
    val orientation: VertexOrientation.VertexOrientation = co._2

    new ChevronPosition(coordinate, orientation)
  }

  val hexagonPiecesConfiguration: ValidBoard.PiecesConfigSpec = (
    hexagonPositions,
    IndexedSeq(
      (resourceTiles, resourceChits),
      (lakeTiles, lakeChits)))
  val chevronPiecesConfiguration: ValidBoard.PiecesConfigSpec = (
    chevronPositions,
    IndexedSeq(
      (fisheryTiles, fisheryChits)))
  val trianglePiecesConfiguration = SmallBoard.trianglePiecesConfiguration

  def board = ValidBoard(hexagonPiecesConfiguration, chevronPiecesConfiguration, trianglePiecesConfiguration)

  val sampleSize = math.pow(6, 6).round
  def count = ValidCount(hexagonPiecesConfiguration, trianglePiecesConfiguration)(sampleSize.asInstanceOf[Int])
  def probability = count.asInstanceOf[Double] / sampleSize
}
