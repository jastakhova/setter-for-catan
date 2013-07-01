package sfc.board

import sfc.pieces._
import sfc.pieces.Tile._
import sfc.placement._
import sfc.placement.VertexOrientation._
import util.Random

/**
 * @author noel.yap@gmail.com
 */
object ValidBoard {
  type PiecesConfigSpec = Pair[IndexedSeq[Position], IndexedSeq[Pair[IndexedSeq[Tile], IndexedSeq[Chits]]]]

  def apply(piecesConfigSpec: PiecesConfigSpec*): Board = {
    val configuration: Board.Configuration = (piecesConfigSpec flatMap { it =>
      val coordinates = it._1
      val tileSets = it._2

      val tileSet = (tileSets map { it =>
        shuffleZip(it._1, it._2)
      }).flatten

      coordinates zip Random.shuffle(tileSet)
    }).toMap
    println(configuration)
    val board = Board(configuration)

    if (board.check) {
      board
    } else {
      apply(piecesConfigSpec: _*)
    }
  }

  private def shuffleZip[T, U](lhs: IndexedSeq[T], rhs: IndexedSeq[U]): IndexedSeq[Pair[T, U]] = {
    require(lhs.size == rhs.size)

    Random.shuffle(lhs) zip Random.shuffle(rhs)
  }
}
