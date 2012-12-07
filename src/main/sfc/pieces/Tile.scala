package sfc.pieces

import play.api.libs.json.JsString

/**
 * @author noel.yap@gmail.com
 */
object Tile extends Enumeration {
  type Tile = Value

  val AnyPort = Value
  val BrickPort = Value
  val Desert = Value
  val Field = Value
  val Fishery = Value
  val Forest = Value
  val GoldField = Value
  val GrainPort = Value
  val Hill = Value
  val Lake = Value
  val LumberPort = Value
  val Mountain = Value
  val OrePort = Value
  val Pasture = Value
  val Sea = Value
  val WoolPort = Value

  class TileValue(tile: Value) {
    def toJson = JsString(tile.toString)

    def *(i: Int): IndexedSeq[Tile] = {
      (1 to i) map { _ =>
        tile
      }
    }
  }

  implicit def value2TileValue(Tile: Value) = new TileValue(Tile)
}
