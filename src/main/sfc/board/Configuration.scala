package sfc.board

import scala.util.Random
import sfc.placement.Position
import sfc.pieces.Chits
import sfc.pieces.Tile._

/**
 * @author noel.yap@gmail.com
 */
class Configuration(val configuration: Map[Position, Pair[Tile, Chits]])

object Configuration {
  type PiecesConfigSpec = Pair[IndexedSeq[Position], IndexedSeq[Pair[IndexedSeq[Tile], IndexedSeq[Chits]]]]

  def apply(piecesConfigSpec: PiecesConfigSpec*): Configuration = {
    new Configuration((piecesConfigSpec flatMap {
      it =>
        val coordinates = it._1
        val tileSets = it._2

        val tileSet = (tileSets map {
          it =>
            shuffleZip(it._1, it._2)
        }).flatten

        coordinates zip Random.shuffle(tileSet)
    }).toMap)
  }

  private def shuffleZip[T, U](lhs: IndexedSeq[T], rhs: IndexedSeq[U]): IndexedSeq[Pair[T, U]] = {
    require(lhs.size == rhs.size)

    Random.shuffle(lhs) zip Random.shuffle(rhs)
  }
}
