package sfc.board

import sfc.pieces._
import sfc.pieces.Tile._
import sfc.placement._
import util.Random

/**
 * @author noel.yap@gmail.com
 */
object ValidCount {
  type PiecesConfigSpec = Pair[IndexedSeq[Position], IndexedSeq[Pair[IndexedSeq[Tile], IndexedSeq[Chits]]]]

  // TODO: use Akka
  def apply(piecesConfigSpec: PiecesConfigSpec*): (Int => Int) = { sampleSize: Int =>
    var result = 0

    var i = 0
    for (i <- 1 to sampleSize) {
      // TODO: refactor duplicate code with ValidBoard.apply()
      val configuration: Board.Configuration = (piecesConfigSpec flatMap { it =>
        val coordinates = it._1
        val tileSets = it._2

        val tileSet = (tileSets map { it =>
          shuffleZip(it._1, it._2)
        }).flatten

        coordinates zip Random.shuffle(tileSet)
      }).toMap
      val board = Board(configuration)

      if (board.check) {
        result += 1
      }
    }

    result
  }

  // TODO: refactor into class so it can be used by ValidBoard
  private def shuffleZip[T, U](lhs: IndexedSeq[T], rhs: IndexedSeq[U]): IndexedSeq[Pair[T, U]] = {
    require(lhs.size == rhs.size)

    Random.shuffle(lhs) zip Random.shuffle(rhs)
  }
}
