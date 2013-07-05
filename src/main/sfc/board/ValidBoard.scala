package sfc.board

/**
 * @author noel.yap@gmail.com
 */
object ValidBoard {
  def apply(piecesConfigSpec: Configuration.PiecesConfigSpec*): Board = {
    val board = Board(piecesConfigSpec: _*)

    if (board.check) {
      board
    } else {
      apply(piecesConfigSpec: _*)
    }
  }
}
